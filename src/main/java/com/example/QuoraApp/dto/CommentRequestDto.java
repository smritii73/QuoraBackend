package com.example.QuoraApp.dto;

import com.example.QuoraApp.models.TargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    @NotBlank(message = "The text is required")
    @Size(min=2, max = 500,message = "The text should be between 2 to 500 characters")
    private String text;

    @NotNull(message = "The targetId is required") //notnull for objects or anything other than string
    private String targetId;

    @NotNull(message = "Target type is required")
    private TargetType targetType;

    @NotBlank(message = "The createdById is required")
    private String createdById;

}
