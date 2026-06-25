package com.attendance_api.domain.service;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.core.exception.FamilyMismatchException;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.repository.ChildRepository;
import com.attendance_api.domain.specification.ChildSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final AttendanceService attendanceService;

    public Long getRegisteredChildren() {
        return childRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<Child> searchChildren(ChildFilterDTO filter, Pageable pageable) {
        return childRepository.findAll(
                ChildSpecification.withFilter(filter),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public Child getChildDetailsByChildId(Long childId) {
        return childRepository.findWithDetailsById(childId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Child not found with id: " + childId));
    }

    @Transactional
    public void updateChildTeamByChildIdAndTeamId(Long childId, Long teamId) {
        childRepository.updateChildTeam(childId, teamId);
    }

    @Transactional
    public void registerCheckin(Long childId, String familyKey) {
        Child child = childRepository.findByIdWithLock(childId).orElseThrow(EntityNotFoundException::new);
        if (Objects.nonNull(familyKey) && !child.getFamily().getFamilyKey().equals(familyKey)) {
            throw new FamilyMismatchException("This child does not belong to this family");
        }
        attendanceService.checkin(child);
    }

    @Transactional
    public void registerCheckout(Long childId, String familyKey) {
        Child child = childRepository.findByIdWithLock(childId).orElseThrow(EntityNotFoundException::new);
        if (!child.getFamily().getFamilyKey().equals(familyKey)) {
            throw new FamilyMismatchException("This child does not belong to this family");
        }
        attendanceService.checkout(child);
    }

    @Transactional
    public void removeCheckin(Long childId, String familyKey) {
        Child child = childRepository.findByIdWithLock(childId).orElseThrow(EntityNotFoundException::new);
        if (Objects.nonNull(familyKey) && !child.getFamily().getFamilyKey().equals(familyKey)) {
            throw new FamilyMismatchException("This child does not belong to this family");
        }
        attendanceService.removeCheckin(child);
    }

    @Transactional
    public void removeCheckout(Long childId, String familyKey) {
        Child child = childRepository.findByIdWithLock(childId).orElseThrow(EntityNotFoundException::new);
        if (!child.getFamily().getFamilyKey().equals(familyKey)) {
            throw new FamilyMismatchException("This child does not belong to this family");
        }
        attendanceService.removeCheckout(child);
    }
}
