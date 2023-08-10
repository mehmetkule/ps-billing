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
@NoArgsConstructor
@Builder
@Log4j2
@AllArgsConstructor
@Entity
@Table(name="member_accounts" )
public class MemberAccount implements Serializable{

    @Id
    @Column(name = "id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="code")
    private String code;

    @Column(name="balance")
    private double balance;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    private Invoice invoice;

    @Column(name="created_date")
    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public MemberAccount(String name, String surname, double balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public MemberAccount(Long id, String name, String surname, double balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public String getCode(){
        return  id + name.substring(0,2);
    }

}
