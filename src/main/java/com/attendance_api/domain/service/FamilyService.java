package com.attendance_api.domain.service;

import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    public Family getFamilyByKey(String key) {
        return familyRepository.getFamilyByFamilyKey(key);
    }
}
