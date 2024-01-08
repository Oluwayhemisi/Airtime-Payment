package com.example.airtime.payments.payload;

import com.example.airtime.payments.entity.Role;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Role roles;

}
