package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Child;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long>, JpaSpecificationExecutor<Child> {
    @EntityGraph(attributePaths = {"team", "family", "family.responsibles", "family.responsibles.contact"})
    @Query("SELECT c FROM Child c WHERE c.childId = :id")
    Optional<Child> findWithDetailsById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Child c SET c.team.id = :teamId WHERE c.id = :childId")
    void updateChildTeam(
            @Param("childId") Long childId,
            @Param("teamId") Long teamId
    );
}
