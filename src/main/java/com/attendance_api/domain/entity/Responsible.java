package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "\"TB_RESPONSIBLE\"")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Responsible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long responsibleId;

    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    private Family family;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
}
