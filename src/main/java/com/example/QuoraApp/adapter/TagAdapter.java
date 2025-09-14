package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import com.example.QuoraApp.models.Tag;

import java.time.LocalDateTime;

public class TagAdapter {
    public static TagResponseDto toDto(Tag tag){
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .usageCount(tag.getUsageCount())
                .createdAt(tag.getCreatedAt())
                .build();
    }

    public static Tag toEntity(TagRequestDto tagRequestDto){
        return Tag.builder()
                .name(tagRequestDto.getName())
                .description(tagRequestDto.getDescription())
                .usageCount(0) //ek req bheji hai toh naya banate waqt 0 should be count
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
