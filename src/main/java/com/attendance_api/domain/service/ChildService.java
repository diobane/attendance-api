package com.attendance_api.domain.service;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.repository.ChildRepository;
import com.attendance_api.domain.specification.ChildSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;

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
}
