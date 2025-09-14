package com.example.QuoraApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min= 10, max = 100, message = "Title must be between 10 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min= 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;

    @Size(max = 10, message = "Maximum 10 question association allowed")
    // tagsIds to associate with the question
    private List<String> tagIds;

}