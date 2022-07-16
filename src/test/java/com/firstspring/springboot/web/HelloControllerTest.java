package com.firstspring.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈(Bean)을 주입 받는다.
    private MockMvc mvc;
    /*
     * 웹 API를 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다
     */

    @Test
    public void hello_return() throws Exception {
        String hello = "hello";

        /*
         * MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.
         * 체이닝이 지원되어 여러 검증 기능을 이어서 선언할 수 있다!
         */

        this.mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 710;

        this.mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.amount").exists());
    }
}