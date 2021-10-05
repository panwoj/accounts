package com.wpirog.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Long id;
    private String nrb;
    private String currency;
    private BigDecimal availableFunds;
}
