package com.example.QuoraApp.adapter;


import com.example.QuoraApp.dto.FollowRequestDto;
import com.example.QuoraApp.dto.FollowResponseDto;
import com.example.QuoraApp.models.Follow;

public class FollowAdapter {

    public static FollowResponseDto toDto(Follow follow){
        return FollowResponseDto.builder()
                .id(follow.getId())
                .followingId(follow.getFollowingId())
                .followerId(follow.getFollowerId())
                .createdAt(follow.getCreatedAt())
                .build();
    }

    public static Follow toEntity(FollowRequestDto followRequestDto){
        return Follow.builder()
                .followingId(followRequestDto.getFollowingId())
                .followerId(followRequestDto.getFollowerId())
                .build();
    }
}
