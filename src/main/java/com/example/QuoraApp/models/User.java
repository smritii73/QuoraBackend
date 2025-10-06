package com.example.QuoraApp.models;

import jakarta.validation.constraints.Email;
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
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank(message = "Username is required")
    @Size(min = 8, message = "The username should be atleast of 8 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Indexed(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 500, message = "The Bio should not exceed 500 characters")
    private String bio;

    @Builder.Default
    private Integer followerCount = 0;

    @Builder.Default
    private Integer followingCount = 0;

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
