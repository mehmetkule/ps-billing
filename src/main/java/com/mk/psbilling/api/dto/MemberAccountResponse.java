package com.mk.psbilling.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mk.psbilling.db.Invoice;
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
public class MemberAccountResponse {

    protected Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("code")
    private String code;

    @JsonProperty("balance")
    private double balance;

    @JsonProperty("invoice")
    private Invoice invoice;
}
