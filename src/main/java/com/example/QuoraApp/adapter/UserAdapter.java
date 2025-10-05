package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.UserRequestDto;
import com.example.QuoraApp.dto.UserResponseDto;
import com.example.QuoraApp.models.User;

public class UserAdapter {

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User toEntity(UserRequestDto userRequestDto){
        return User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .bio(userRequestDto.getBio())
                .build();
    }
}