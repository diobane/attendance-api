package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "\"TB_FAMILY\"")
@Data
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMILY_ID")
    private Long familyId;

    private String familyKey;

    private Boolean addedWhatsForInfo;

    private Boolean badgeAcknowledgment;

    private Boolean userInfoAcknowledgment;

    // O cascade e orphanRemoval garantem que filhos sejam salvos junto com a família
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Responsible> responsibles;
}
