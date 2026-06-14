package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>, JpaSpecificationExecutor<Family> {
    Family getFamilyByFamilyKey(String key);
}
