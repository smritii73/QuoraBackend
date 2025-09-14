package com.example.QuoraApp.dto;

import com.example.QuoraApp.models.LikeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponseDto {
    // here, we only take the variables not the checks as response doesnt need checks
    private String id;
    private String targetId;
    private LikeType likeType;
    private Boolean isLike; //denotes whether liked or disliked
    private LocalDateTime createdAt;
}