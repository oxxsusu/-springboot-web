/* 컨트롤러 단위로 클래스 작성 */
package com.firstspring.springboot.web;

import com.firstspring.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
public class HelloController {

    @GetMapping("/hello") // HTTP GET요청을 받을 수 있는 API를 만들어 준다.
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return  new HelloResponseDto(name, amount);
    }
    /* RequestParam -> 외부에서 API 로 넘긴 파라미터를 가져오는 어노테이션.
    * 여기서는 외부에서 name 이라는 이름으로 넘긴 파라미터를 메소드 파라미터 name 에 저장하게 됨 */
}
