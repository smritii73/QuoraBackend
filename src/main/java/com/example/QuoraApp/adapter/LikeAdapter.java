package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;
import com.example.QuoraApp.models.Like;

import java.time.LocalDateTime;

public class LikeAdapter {
   public static LikeResponseDto toDto(Like like){
       return LikeResponseDto.builder()
               .id(like.getId())
               .targetId(like.getTargetId())
               .isLike(like.getIsLike())
               .likeType(like.getLikeType())
               .createdAt(like.getCreatedAt())
               .build();
   }

   public static  Like toEntity(LikeRequestDto likeRequestDto){
       return Like.builder()
               .targetId(likeRequestDto.getTargetId())
               .likeType(likeRequestDto.getLikeType())
               .isLike(likeRequestDto.getIsLike())
               .createdAt(LocalDateTime.now())
               .updatedAt(LocalDateTime.now())
               .build();
   }
}
