package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Attendance;
import com.attendance_api.domain.enums.AttendanceType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findTopByChild_ChildIdAndTypeOrderByCreatedAtDesc(Long childId, AttendanceType type);

    @EntityGraph(attributePaths = "child")
    List<Attendance> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    void deleteByChild_ChildIdAndTypeAndCreatedAtBetween(
            Long childId, AttendanceType type, LocalDateTime start, LocalDateTime end);
}
