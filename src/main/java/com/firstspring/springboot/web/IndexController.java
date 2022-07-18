package com.firstspring.springboot.web;

import com.firstspring.springboot.service.posts.PostsService;
import com.firstspring.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping
    public String index() {
        return "index";
        /*
         * 머스테치 스타터가 있어서 컨트롤러에서 index 라는 문자열을 반환할 때
           앞의 경로와 뒤의 파일 확장자가 자동으로 지정된다.
         * src/main/resources/templates/index.mustache 의 경로로 전환되어
           ViewResolver 라는 URL 요청 관리자가 알아서 처리한다.
         */
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpade(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id); // Service에서 id로 게시글을 찾고
        model.addAttribute("post", dto);

        return "posts-update"; // 연결될 머스테치 파일명을 리턴
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save"; // "/posts/save"를 호출하면 posts-save.mustache 를 호출하겠다는 메소드
    }
}
