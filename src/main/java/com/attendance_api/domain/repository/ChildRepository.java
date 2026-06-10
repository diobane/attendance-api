package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Child;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long>, JpaSpecificationExecutor<Child> {
    @EntityGraph(attributePaths = {"team", "family", "family.responsibles", "family.responsibles.contact"})
    @Query("SELECT c FROM Child c WHERE c.childId = :id")
    Optional<Child> findWithDetailsById(@Param("id") Long id);
}
