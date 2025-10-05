package com.example.QuoraApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 8, message = "The username should be atleast of 8 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 500, message = "The Bio should not exceed 500 characters")
    private String bio;
}
