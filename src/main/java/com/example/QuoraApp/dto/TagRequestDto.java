package com.example.QuoraApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Getter // if a variable is private and we require it, we get it via getter eg .name
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {

    @NotBlank(message = "Tag name is required")
    @Size(min=2,max=50, message = "The tag name should be between 2 - 50 characters")
    @Indexed(unique=true)
    private String name;

    @Size(max=200, message = "The description should not exceed 200 characters")
    private String description;
}
