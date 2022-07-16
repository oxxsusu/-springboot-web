package com.firstspring.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity // JPA 의 어노테이션, 데이터 테이블과 연결될 클래스임을 나타낸다.
// 클래스의 카멜케이스 네이밍과 달리 테이블의 이름은 언더스코어(a_b) 네이밍으로 매칭한다.
// 엔티티 클래스에서는 절대 Setter 메소드를 만들지 않는다.
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 괄호 안은 PK의 생성 규칙을 나타낸다.
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타냄
    // 굳이 선언 안 해도 되지만 기본값 외의 추가 변경 옵션을 사용하게 될 경우에 대비해 선언한다.
    private String title; // 글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 글 내용

    private String author; // 글 작성자

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
