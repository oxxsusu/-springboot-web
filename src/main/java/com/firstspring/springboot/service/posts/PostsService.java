package com.firstspring.springboot.service.posts;

import com.firstspring.springboot.domain.posts.Posts;
import com.firstspring.springboot.domain.posts.PostsRepository;
import com.firstspring.springboot.web.dto.PostsResponseDto;
import com.firstspring.springboot.web.dto.PostsSaveRequestDto;
import com.firstspring.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("오류가 발생했습니다. 게시글 id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("오류가 발생했습니다. 게시글 id=" + id));

        return new PostsResponseDto(entity);
    }
}
