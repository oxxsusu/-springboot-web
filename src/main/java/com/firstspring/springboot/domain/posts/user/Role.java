package com.firstspring.springboot.domain.posts.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");
    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 한다.
    // 그래서 코드별로 키값을 ROLE_GUEST, ROLE_USER 로 지정한 것.

    private final String key;
    private final String title;
}