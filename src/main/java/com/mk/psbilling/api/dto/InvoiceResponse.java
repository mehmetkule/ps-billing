package com.mk.psbilling.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
@Data
@NoArgsConstructor
@Builder
@Log4j2
@AllArgsConstructor
public class InvoiceResponse {
    protected Long id;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("bill_type")
    private int billType;

    @JsonProperty("process_date")
    private Date process_date;
}
