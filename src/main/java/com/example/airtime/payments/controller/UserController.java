package com.example.airtime.payments.controller;

import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.LoginRequest;
import com.example.airtime.payments.payload.LoginResponseDto;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.payload.UserResponse;
import com.example.airtime.payments.service.UserService;
import com.example.airtime.payments.usersecurity.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;




    @PostMapping("/register")
    public ResponseEntity<UserResponse> Register(@Valid @RequestBody UserRequest userDto){
        UserResponse response = userService.register(userDto);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequest loginDto) throws UserException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userService.findUserByEmail(loginDto.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserName(user.getUserName());
        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setToken(token);
        return ResponseEntity.ok(loginResponseDto);
    }
}
