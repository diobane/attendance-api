package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"TB_TEAM\"")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;
    private String name;
    private String color;
}
