package com.example.airtime.payments.payload;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String userName;
   private String email;
    private String token;
}
