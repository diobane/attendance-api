package com.attendance_api.domain.entity;

import com.attendance_api.domain.converter.AttendanceTypeConverter;
import com.attendance_api.domain.enums.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"TB_ATTENDANCE\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDANCE_ID")
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Convert(converter = AttendanceTypeConverter.class)
    @Column(name = "TYPE", length = 1)
    private AttendanceType type;
}
