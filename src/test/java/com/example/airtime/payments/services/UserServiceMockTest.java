package com.example.airtime.payments.services;


import com.example.airtime.payments.entity.Role;
import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.payload.UserResponse;
import com.example.airtime.payments.repository.UserRepository;
import com.example.airtime.payments.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceMockTest {

    @Mock
    @Autowired
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws UserException {

        UserRequest userRequest = new UserRequest(4L,"yem", "haley@gmail.com",  "1234", Role.ROLE_USER);
        User savedUser = new User();
        savedUser.setId(4L);
        savedUser.setEmail("haley@gmail.com");
        savedUser.setUserName("yem");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("$2a$10$49lPTeJdhVQGKw3S1ltnuOh8acksO5CKl4/dyKhNskPHEF2TDP6FG");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(new UserResponse(4L,"yem", "haley@gmail.com",  LocalDate.now(), Role.ROLE_USER));

        UserResponse result = userService.register(userRequest);

        assertNotNull(result);
        assertEquals(7L, result.getId());
        assertEquals("haley@gmail.com", result.getEmail());
        assertEquals("yem", result.getUserName());

    }




}
