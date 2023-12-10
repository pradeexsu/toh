package com.example.javatemplate.rest.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    List<UserDto> userList;
}
