package com.example.QuoraApp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {
    private String id;
    private String content;
    private String questionId;
    private LocalDateTime createdAt;
}