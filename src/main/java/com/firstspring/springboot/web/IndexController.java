package com.firstspring.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

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

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save"; // "/posts/save"를 호출하면 posts-save.mustache 를 호출하겠다는 메소드
    }
}
