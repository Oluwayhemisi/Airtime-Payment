package com.example.airtime.payments.payload;


import com.example.airtime.payments.entity.AirtimeDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AirtimeRequest {
    private String requestId;
    private String phoneNumber;
    private BigDecimal amount;
    private String uniqueCode;

}
