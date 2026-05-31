package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"TB_CHILD\"")
@Data
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childId;

    private String fullName;
    private Integer age;
    private String attendsChurch;
    private String medicine;
    private String dietaryRestriction;
    private String observation;
    private String guardianName;
    private Boolean isAdventist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    private Family family; // Referência preenchida pelo @AfterMapping do Mapper
}
