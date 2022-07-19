package com.firstspring.springboot.config.auth;

import com.firstspring.springboot.config.auth.dto.OAuthAttributes;
import com.firstspring.springboot.config.auth.dto.SessionUser;
import com.firstspring.springboot.domain.posts.user.User;
import com.firstspring.springboot.domain.posts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration()
                                        .getProviderDetails()
                                        .getUserInfoEndpoint()
                                        .getUserNameAttributeName();
        /* 1) registrationId
        * 현재 로그인 진행 중인 서비스를 구분하는 코드
        * 지금은 구글에서만 사용하지만, 이따 네이버 로그인까지 연동하게 되면
        * 지금 로그인한 계정이 네이버 로그인인지 구글인지 구분해주는 역할을 한답니다 */
        String userNameAttributeName = userRequest.getClientRegistration()
                                        .getProviderDetails()
                                        .getUserInfoEndpoint()
                                        .getUserNameAttributeName();
        /* 2) userNameAttributeName
        * OAuth2 로그인 진행 시 키가 되는 필드값을 의미 ( = Primary Key 역할)
        * 구글의 경우 기본적으로 코드를 지원하지만, 네이버나 카카오 등은 기본으로 지원하지 않는다.
        * 구글의 기본 코드는 'sub' 이다.
        * 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용하게 된다.

        * 3) OAuthAttributes
        * OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스이다.
        * 이후 네이버 등의 다른 소셜 로그인에서도 이 클래스를 사용하게 될 것 */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
        private User saveOrUpdate(OAuthAttributes attributes) {
            User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());

            return  userRepository.save(user);
    }
}
