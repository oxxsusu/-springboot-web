package com.firstspring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화 어노테이션
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행구문
        /* 스프링 부트에서는 내장 WAS 를 사용하는 것을 권장하는데,
           언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있기 때문이다 */
    }
}
