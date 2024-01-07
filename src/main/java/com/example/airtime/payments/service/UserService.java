package com.example.airtime.payments.service;

import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.UserRequest;

public interface UserService {

    public User findUserByEmail(String email) throws UserException;

    String register(UserRequest userDto) throws UserException;


}
