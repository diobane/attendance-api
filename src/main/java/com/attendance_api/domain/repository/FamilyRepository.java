package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>, JpaSpecificationExecutor<Family> {
    Family getFamilyByFamilyKey(String key);

    /**
     * Transaction-level advisory lock used to serialize concurrent family
     * registrations so the enrollment limit can never be exceeded by
     * simultaneous requests. Released automatically when the transaction ends.
     */
    @Query(value = "SELECT 1 FROM (SELECT pg_advisory_xact_lock(:key)) AS lock_acquired", nativeQuery = true)
    Integer acquireRegistrationLock(@Param("key") long key);
}
