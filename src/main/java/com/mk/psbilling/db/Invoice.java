package com.mk.psbilling.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name="invoices")
public class Invoice implements Serializable{
    @Id
    @Column(name = "id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="amount")
    private double amount;

    @Column(name="bill_type")
    private int billType;

    public Invoice(Long id, double amount, int billType) {
        this.id = id;
        this.amount = amount;
        this.billType = billType;
    }

    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="process_date")
    private Date process_date;

    @Column(name="created_date")
    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
