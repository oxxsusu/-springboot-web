package com.firstspring.springboot.config.auth.dto;

import com.firstspring.springboot.domain.posts.user.Role;
import com.firstspring.springboot.domain.posts.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /* 1) of() 메소드
     * OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 반환해야만 한다..! */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /* 2) toEntity() 메소드
     * User 엔티티를 생성해준다.
     * OAuthAttributes 에서 엔티티를 생성하는 시점은 처음 가입할 때이다.
     * 가입할 떄의 기본 권한을 GUEST 로 주기 위해서 role 빌더값에는 Role.GUEST 를 사용 */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture((picture))
                .role(Role.GUEST)
                .build();
    }
}

