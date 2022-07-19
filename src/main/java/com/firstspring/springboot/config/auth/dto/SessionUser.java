package com.firstspring.springboot.config.auth.dto;

import com.firstspring.springboot.domain.posts.user.User;
import lombok.Getter;

import java.io.*;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
