package com.nus.assessment.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = "accountId"))
@Data
public class Account {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id; 

    private String accountId;
    
    @Column(length = 2000)
    private String privateKey;

    // constructor
    public Account() {}

    public Account(String accountId, String privateKey) {
        this.accountId = accountId;
        this.privateKey = privateKey;
    }

}
