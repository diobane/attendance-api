package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.Attendance;
import com.attendance_api.domain.enums.AttendanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findTopByChild_ChildIdAndTypeOrderByCreatedAtDesc(Long childId, AttendanceType type);
}
