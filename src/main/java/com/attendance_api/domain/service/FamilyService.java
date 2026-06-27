package com.attendance_api.domain.service;

import com.attendance_api.api.dto.FamilyFilterDTO;
import com.attendance_api.core.exception.DuplicatedFamilyKeyException;
import com.attendance_api.core.exception.RegistrationLimitReachedException;
import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.repository.FamilyRepository;
import com.attendance_api.domain.specification.FamilySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    @Value("${app.registration.limit:200}")
    private long registrationLimit;

    /**
     * Arbitrary but fixed key shared by every registration so they all contend
     * for the same advisory lock.
     */
    private static final long REGISTRATION_LOCK_KEY = 4202026L;

    @Transactional
    public Family saveFamily(Family family) {
        // Serialize concurrent registrations so the limit check below is atomic:
        // only one transaction at a time can count + insert, preventing overshoot.
        familyRepository.acquireRegistrationLock(REGISTRATION_LOCK_KEY);

        if (familyRepository.count() >= registrationLimit) {
            throw new RegistrationLimitReachedException(
                    "As inscrições foram encerradas. O limite de " + registrationLimit +
                            " inscrições foi atingido."
            );
        }

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
