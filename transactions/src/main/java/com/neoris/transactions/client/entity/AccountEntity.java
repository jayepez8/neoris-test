package com.neoris.transactions.client.entity;

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
 * @author jyepez on 19/7/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "client_id", nullable = false)
    private Integer clientId;

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

    @PrePersist
    public void prePersist() {
        this.status = Boolean.TRUE;
        this.createdDate = LocalDateTime.now();
    }
}
