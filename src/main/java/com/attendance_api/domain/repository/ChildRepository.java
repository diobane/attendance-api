package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Child;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long>, JpaSpecificationExecutor<Child> {
    @EntityGraph(attributePaths = {"team", "family", "family.responsibles"})
    @QueryHints(value = { @QueryHint(name = "hibernate.query.passDistinctThrough", value = "false") })
    List<Child> findAll(Specification<Child> spec);
}
