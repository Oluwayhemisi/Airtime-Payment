package com.example.airtime.payments.service;

import com.example.airtime.payments.entity.Role;
import com.example.airtime.payments.entity.User;
import com.example.airtime.payments.exceptions.UserException;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;







    private void validateIfUserExist(UserRequest userDto) throws UserException {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new UserException("User already exist.", HttpStatus.CONFLICT);
        }
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("user does not exist", HttpStatus.NOT_FOUND));
    }



    @Override
    public String register(UserRequest userDto) throws UserException {
    validateIfUserExist(userDto);
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setUserName(userDto.getUserName());
    user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Registered successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), getAuthorities(Collections.singleton(Role.ROLE_USER)));
        }

        return null;


    }
    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }
}
