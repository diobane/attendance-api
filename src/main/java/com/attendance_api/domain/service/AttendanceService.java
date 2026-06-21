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

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

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
}
