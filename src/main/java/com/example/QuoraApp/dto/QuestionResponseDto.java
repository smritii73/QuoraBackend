package com.example.QuoraApp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDto {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}