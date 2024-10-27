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
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID", updatable = false)
    private Integer clientID;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "STATUS", length = 1, nullable = false)
    private Boolean status;

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

    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @PrePersist
    public void prePersist() {
        this.status = Boolean.TRUE;
        this.createdDate = LocalDateTime.now();
    }
}

