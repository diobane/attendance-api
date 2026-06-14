package com.attendance_api.domain.service;

import com.attendance_api.api.dto.FamilyFilterDTO;
import com.attendance_api.core.exception.DuplicatedFamilyKeyException;
import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.repository.FamilyRepository;
import com.attendance_api.domain.specification.FamilySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    public Family saveFamily(Family family) {
        Optional.ofNullable(getFamilyByKey(family.getFamilyKey()))
                .ifPresent(existing -> {
                    throw new DuplicatedFamilyKeyException(
                            "It was not possible to create this family. " +
                                    "There's already a family with this same key"
                    );
                });

        return familyRepository.save(family);
    }

    public Family getFamilyByKey(String key) {
        return familyRepository.getFamilyByFamilyKey(key);
    }

    @Transactional(readOnly = true)
    public Page<Family> searchFamilies(FamilyFilterDTO filter, Pageable pageable) {
        return familyRepository.findAll(
                FamilySpecification.withFilter(filter),
                pageable
        );
    }
}
