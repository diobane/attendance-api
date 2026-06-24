package com.attendance_api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"TB_CONTACT\"")
@Data
@org.hibernate.annotations.BatchSize(size = 20)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "PHONE_1", length = 20)
    private String phone1;

    @Column(name = "PHONE_2", length = 20)
    private String phone2;

    @Column(name = "EMAIL", length = 100)
    private String email;
}
