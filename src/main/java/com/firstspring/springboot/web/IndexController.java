package com.firstspring.springboot.web;

import com.firstspring.springboot.config.auth.LoginUser;
import com.firstspring.springboot.config.auth.dto.SessionUser;
import com.firstspring.springboot.service.posts.PostsService;
import com.firstspring.springboot.web.dto.PostsResponseDto;
import com.mysql.cj.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        /* @LoginUser 어노테이션을 사용하여 세션유저를 부르면 로그인된 사용자의 정보를 바로 가지고 올 수 있도록 어노테이션 기반으로 개선 */
        model.addAttribute("posts", postsService.findAllDesc());
        /* 앞서 CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 을 저장하도록 했음.
        * 따라서 로그인 성공 시 httpSession.getAttribute("user") 에서 값을 가져올 수 있게 됨. */
        if (user != null) {
            model.addAttribute("userName", user.getName());
            /* 세션에 저장된 값이 있을 때만 model에 userName 으로 등록하게 하고,
            * 세션에 저장된 값이 없으면 model에 아무 값이 없는 상태이기 때문에 로그인 버튼이 보이게 한다. */
        }
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
