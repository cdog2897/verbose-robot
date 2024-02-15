package com.gcu.registrationlogin.service;

import com.gcu.registrationlogin.dto.UserDto;
import com.gcu.registrationlogin.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}