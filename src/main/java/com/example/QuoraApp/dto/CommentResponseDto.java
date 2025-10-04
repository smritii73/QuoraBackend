package com.example.QuoraApp.dto;

import com.example.QuoraApp.models.LikeType;
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
    private LocalDateTime createdAt;
}
