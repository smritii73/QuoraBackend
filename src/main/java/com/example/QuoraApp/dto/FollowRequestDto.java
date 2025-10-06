package com.example.QuoraApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRequestDto {

    @NotBlank(message = "followerId is required")
    private String followerId;

    @NotBlank(message = "followingId is required")
    private String followingId;
}
