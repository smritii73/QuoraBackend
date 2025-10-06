package com.example.QuoraApp.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponseDto {

    private String id;
    private String followerId;
    private String followingId;
    private LocalDateTime createdAt;
}
