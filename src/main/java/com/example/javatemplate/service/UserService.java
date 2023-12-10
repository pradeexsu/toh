package com.example.javatemplate.service;

import com.example.javatemplate.persistance.modal.User;
import com.example.javatemplate.persistance.repository.UserRepository;
import com.example.javatemplate.rest.response.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserDto> fetchUserDetails() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::buildUserDto)
                .collect(Collectors.toList());
    }

    private UserDto buildUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .build();
    }
}
