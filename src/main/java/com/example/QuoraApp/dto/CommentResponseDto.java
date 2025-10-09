package com.example.QuoraApp.dto;

import com.example.QuoraApp.models.TargetType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private String id;
    private String text;
    private String targetId;
    private TargetType targetType;
    private UserResponseDto createdByUser;
    private LocalDateTime createdAt;
}
