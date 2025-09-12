package com.example.QuoraApp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "answers") // this is same as @Entity in sql but here we have MongoDB
public class Answer {

    @Id
    private String id;

    @NotBlank
    @Size(min=10, max=1000,message = "Content must be between 10 to 1000 characters.")
    private String content;

    /* A question can have multiple answers but 1 answer belongs onyl to 1 question
     We can use @Indexed annotation to create an index on this property
      Index is created to create a data structure of that property on whch the index is created so that we can
      query it faster */

    @Indexed
    private String questionId;

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}