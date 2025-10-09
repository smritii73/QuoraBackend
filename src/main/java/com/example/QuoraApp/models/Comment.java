package com.example.QuoraApp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @NotBlank(message="The text is required")
    @Size(min = 2, max = 500, message = "The comment should be from 2 to 500 characters.")
    private String text;

    @NotBlank(message = "The targetId is required")
    private String targetId;

    @NotNull(message = "Target type is required")
    private TargetType targetType;

    @NotBlank(message = "The createdById is required")
    private String createdById; // userId

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
