package com.firstspring.springboot.web.dto;

import com.firstspring.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListsResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate; //최종수정일

    public PostsListsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
