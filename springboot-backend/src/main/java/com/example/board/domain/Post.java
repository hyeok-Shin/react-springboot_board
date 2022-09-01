package com.example.board.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //서버 실행시 테이블 생성

public class Post {
    @Id //pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스 번호증가 전략 적용
    private Long id;

    private Long originNo;

    @ColumnDefault("0")
    private Long layer;

    private String title;
    private String content;

    private String originFileName;

    private String fileName;


    private String filePath;


}
