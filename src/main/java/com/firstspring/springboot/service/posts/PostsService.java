package com.firstspring.springboot.service.posts;

import com.firstspring.springboot.domain.posts.Posts;
import com.firstspring.springboot.domain.posts.PostsRepository;
import com.firstspring.springboot.web.dto.PostsListsResponseDto;
import com.firstspring.springboot.web.dto.PostsResponseDto;
import com.firstspring.springboot.web.dto.PostsSaveRequestDto;
import com.firstspring.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.List;

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

    /*
    새로운 트랜잭션이지만, readOnly=true 를 주어 트랜잭션 범위는 유지하고
    조회 기능만 분리하도록 하여 조회 속도를 개선한다.
    */
    @Transactional(readOnly = true)
    public List<PostsListsResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);
    }
}
