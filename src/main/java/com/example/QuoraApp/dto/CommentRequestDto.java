package com.example.QuoraApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    @NotBlank(message = "The message should be between 2 to 500 characters")
    private String text;

    @NotNull(message = "The targetId is required") //notnull for objects or anything other than string
    private String targetId;
}
