package com.example.airtime.payments.payload;

import com.example.airtime.payments.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Set<Role> roles;

}
