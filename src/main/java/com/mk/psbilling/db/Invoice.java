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
    protected Long id;

    @Column(name="amount")
    private Double amount;

    @Column(name="bill_type")
    private int bill_type;


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
