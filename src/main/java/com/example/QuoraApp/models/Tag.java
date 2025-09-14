package com.example.QuoraApp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="tags")
public class Tag {

    /* One question has many tags and one tag can have many questions */
    @Id
    private String id;

    @NotBlank(message = "Tag name is required")
    @Size(min=2,max=50, message = "The tag name should be between 2 - 50 characters")
    @Indexed(unique=true)
    private String name;

    @Size(max=200, message = "The description should not exceed 200 characters")
    private String description;

    @Builder.Default
    private Integer usageCount = 0; // tracks how many questions use this tag
    // whenerver we have to give default value, use Builder.Default

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
