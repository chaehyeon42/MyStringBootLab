package com.rookies5.myspringbootLab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * 도서 정보를 담는 엔티티 클래스
 * @Entity: JPA가 관리하는 엔티티임을 명시
 * @Table: DB의 'books' 테이블과 매핑
 */
@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 포함한 생성자
@Builder // 빌더 패턴 사용 가능
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마리아DB의 auto_increment 사용
    private Long id;

    @Column(nullable = false) // 필수 값 설정
    private String title;

    private String author;

    @Column(unique = true) // ISBN은 중복될 수 없음
    private String isbn;

    private Integer price;

    private LocalDate publishDate;
}