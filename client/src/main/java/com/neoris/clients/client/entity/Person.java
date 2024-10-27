package com.neoris.clients.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 26/10/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID", updatable = false)
    private Integer personID;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "GENDER", length = 1)
    private Character gender;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "IDENTIFICATION", unique = true, nullable = false)
    private String identification;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "create_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
