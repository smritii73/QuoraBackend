package com.example.QuoraApp.models;

import co.elastic.clients.elasticsearch._types.GeoLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    @NotBlank
    @Size(min = 2 , max = 500, message = "The comment should be from 2 to 500 characters long")
    private String text;

    @NotBlank(message = "The targetId is required") //notblank for string
    private String targetId;

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
