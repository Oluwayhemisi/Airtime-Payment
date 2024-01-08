package com.example.airtime.payments.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeRequest {
    private String requestId;
    private String uniqueCode;
    private String phoneNumber;
    private BigDecimal amount;


}
