package com.example.QuoraApp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> tagIds;
    private List<TagResponseDto> tags;
    private LocalDateTime createdAt;
}