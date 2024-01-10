package com.example.airtime.payments.services;


import com.example.airtime.payments.controller.UserController;
import com.example.airtime.payments.entity.Role;
import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.LoginRequest;
import com.example.airtime.payments.payload.LoginResponseDto;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.payload.UserResponse;
import com.example.airtime.payments.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;



    @Test
    void testToCreateRegisterUser(){
        createUser();
        assertEquals(14,userService.getAllUsers().size());

    }
    private UserResponse createUser() throws UserException {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("aiisha@gmail.com");
        userRequest.setUserName("Addah");
        userRequest.setPassword("5555");
        userRequest.setRoles(Role.ROLE_USER);

        return userService.register(userRequest);
    }


    @Test
    void testRegisterUserThrowsExceptionForExistingUser() throws UserException {
        UserRequest userRequest = createValidUserRequest();
        userService.register(userRequest);

        assertThrows(UserException.class, () -> userService.register(userRequest));
    }

    @Test
    void testFindUserByEmail() throws UserException {
        UserRequest userRequest = createValidUserRequest();
        UserResponse userResponse = userService.register(userRequest);

        User foundUser = userService.findUserByEmail(userRequest.getEmail());

        assertNotNull(foundUser);
        assertEquals(userResponse.getId(), foundUser.getId());
        assertEquals(userResponse.getEmail(), foundUser.getEmail());
        assertEquals(userResponse.getUserName(), foundUser.getUserName());
        assertEquals(userResponse.getRoles(), foundUser.getRoles());
    }


    private UserRequest createValidUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("bee@gmail.com");
        userRequest.setUserName("Bisola");
        userRequest.setPassword("password123");
        userRequest.setRoles(Role.ROLE_USER);
        return userRequest;
    }


    @Test
    void testLoginUser_success() {
        // Set up test data
        LoginRequest loginDto = new LoginRequest("bee@gmail.com", "password123");

        // Call the endpoint
        ResponseEntity<LoginResponseDto> response = userController.loginUser(loginDto);

        // Assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        LoginResponseDto loginResponse = response.getBody();
        assertThat(loginResponse.getUserName()).isEqualTo("Bisola");
        assertThat(loginResponse.getEmail()).isEqualTo("bee@gmail.com");
        assertThat(loginResponse.getToken()).isNotEmpty();
    }

















//
//    @Test
//    void testLoginUserSuccessfully() throws Exception {
//        // Register a user
//        UserRequest userRequest = createValidUserRequest();
//        userService.register(userRequest);
//
//        // Login with the registered user credentials
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail(userRequest.getEmail());
//        loginRequest.setPassword(userRequest.getPassword());
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").isNotEmpty())
//                .andExpect(jsonPath("$.userName").value(userRequest.getUserName()))
//                .andExpect(jsonPath("$.email").value(userRequest.getEmail()));
//    }

}
