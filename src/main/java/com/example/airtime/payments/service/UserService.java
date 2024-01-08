package com.example.airtime.payments.service;

import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.payload.UserResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {

    public User findUserByEmail(String email) throws UserException;

    UserResponse register(UserRequest userDto) throws UserException;


    List<User> getAllUsers();
}
