package com.example.QuoraApp.dto;

import com.example.QuoraApp.models.LikeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequestDto {

    @NotBlank(message = "The targetId is required") //notblank for string
    private String targetId;

    @NotNull(message = "The likeType is required") //notnull for objects or anything other than string
    private LikeType likeType;

    @NotNull(message = "The isLike is required")
    private Boolean isLike; //denotes whether liked or disliked
}
