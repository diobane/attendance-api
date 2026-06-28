package com.attendance_api.domain.service;

import com.attendance_api.core.exception.DuplicateCheckinException;
import com.attendance_api.domain.entity.Attendance;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.enums.AttendanceType;
import com.attendance_api.domain.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    /** Horário de entrada/saída de hoje (mais recente de cada tipo) por criança. */
    public record TodayStatus(LocalDateTime checkinAt, LocalDateTime checkoutAt) {}

    /** Histórico completo de presença (entradas e saídas) de uma criança. */
    @Transactional(readOnly = true)
    public List<Attendance> getHistory(Long childId) {
        return attendanceRepository.findByChild_ChildIdOrderByCreatedAtAsc(childId);
    }

    /**
     * Marca uma entrada/saída em um instante específico, substituindo qualquer
     * registro do mesmo tipo já existente naquele dia (uma marcação por dia).
     */
    @Transactional
    public void markAt(Child child, AttendanceType type, LocalDateTime at) {
        LocalDate day = at.toLocalDate();
        attendanceRepository.deleteByChild_ChildIdAndTypeAndCreatedAtBetween(
                child.getChildId(), type, day.atStartOfDay(), day.plusDays(1).atStartOfDay());

        Attendance attendance = Attendance.builder()
                .child(child)
                .type(type)
                .createdAt(at)
                .build();
        attendanceRepository.save(attendance);
    }

    /** Remove a entrada/saída de um dia específico (desmarcar). */
    @Transactional
    public void unmarkDay(Child child, AttendanceType type, LocalDate day) {
        attendanceRepository.deleteByChild_ChildIdAndTypeAndCreatedAtBetween(
                child.getChildId(), type, day.atStartOfDay(), day.plusDays(1).atStartOfDay());
    }

    @Transactional(readOnly = true)
    public Map<Long, TodayStatus> getTodayStatusByChild() {
        LocalDate today = LocalDate.now();
        List<Attendance> registros = attendanceRepository.findByCreatedAtBetween(
                today.atStartOfDay(), today.plusDays(1).atStartOfDay());

        Map<Long, LocalDateTime> checkins = new HashMap<>();
        Map<Long, LocalDateTime> checkouts = new HashMap<>();
        for (Attendance a : registros) {
            Long childId = a.getChild().getChildId();
            Map<Long, LocalDateTime> alvo = a.getType() == AttendanceType.CHECKIN ? checkins : checkouts;
            alvo.merge(childId, a.getCreatedAt(), (atual, novo) -> novo.isAfter(atual) ? novo : atual);
        }

        Map<Long, TodayStatus> status = new HashMap<>();
        checkins.keySet().forEach(id -> status.put(id, new TodayStatus(checkins.get(id), checkouts.get(id))));
        checkouts.keySet().forEach(id ->
                status.computeIfAbsent(id, k -> new TodayStatus(checkins.get(id), checkouts.get(id))));
        return status;
    }

    @Transactional
    public void checkin(Child child) {
        attendanceRepository
                .findTopByChild_ChildIdAndTypeOrderByCreatedAtDesc(child.getChildId(), AttendanceType.CHECKIN)
                .filter(lastCheckin -> lastCheckin.getCreatedAt().toLocalDate().isEqual(LocalDate.now()))
                .ifPresent(lastCheckin -> {
                    throw new DuplicateCheckinException(
                            "It's not possible to register two check-ins for the same child on the same day.");
                });

        Attendance attendance = Attendance.builder()
                .child(child)
                .type(AttendanceType.CHECKIN)
                .createdAt(LocalDateTime.now())
                .build();
        attendanceRepository.save(attendance);
    }

    @Transactional
    public void checkout(Child child) {
        attendanceRepository
                .findTopByChild_ChildIdAndTypeOrderByCreatedAtDesc(child.getChildId(), AttendanceType.CHECKOUT)
                .filter(lastCheckout -> lastCheckout.getCreatedAt().toLocalDate().isEqual(LocalDate.now()))
                .ifPresent(lastCheckout -> {
                    throw new DuplicateCheckinException(
                            "It's not possible to register two check-outs for the same child on the same day.");
                });

        Attendance attendance = Attendance.builder()
                .child(child)
                .type(AttendanceType.CHECKOUT)
                .createdAt(LocalDateTime.now())
                .build();
        attendanceRepository.save(attendance);
    }

    /** Remove o(s) registro(s) de check-in de hoje da criança (desmarcar entrada). */
    @Transactional
    public void removeCheckin(Child child) {
        removeTodayByType(child, AttendanceType.CHECKIN);
    }

    /** Remove o(s) registro(s) de check-out de hoje da criança (desmarcar retirada). */
    @Transactional
    public void removeCheckout(Child child) {
        removeTodayByType(child, AttendanceType.CHECKOUT);
    }

    private void removeTodayByType(Child child, AttendanceType type) {
        LocalDate today = LocalDate.now();
        attendanceRepository.deleteByChild_ChildIdAndTypeAndCreatedAtBetween(
                child.getChildId(), type, today.atStartOfDay(), today.plusDays(1).atStartOfDay());
    }
}
