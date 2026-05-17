package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"TB_RESPONSIBLE\"")
@Data
public class Responsible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responsibleId;

    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    private Family family; // Referência preenchida pelo @AfterMapping do Mapper

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
}
