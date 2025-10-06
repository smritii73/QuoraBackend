package com.example.QuoraApp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String id;
    private String username;
    private String email;
    private String bio;
    private Integer followingCount;
    private Integer followerCount;
    private LocalDateTime createdAt;
}
