package com.example.QuoraApp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="questions") // this is same as @Entity in SQL but here we have mongoDB
public class Question {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 10,max = 100, message = "Title must be between 10 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10,max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
