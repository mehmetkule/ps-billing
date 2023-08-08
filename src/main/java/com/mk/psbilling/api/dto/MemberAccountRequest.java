package com.mk.psbilling.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mk.psbilling.db.Invoice;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@Builder
@Log4j2
@AllArgsConstructor
public class MemberAccountRequest {

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="balance")
    private double balance;

    @JsonProperty("invoice")
    private Invoice invoice;

}
