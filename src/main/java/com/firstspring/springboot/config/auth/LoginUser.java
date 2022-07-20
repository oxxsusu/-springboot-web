package com.firstspring.springboot.config.auth;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
// 이 어노테이션이 생성될 수 있는 위치를 지정 -> PARAMETER로 정했으니 메소드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // 이 파일을 어노테이션으로 지정하는 구문
}
