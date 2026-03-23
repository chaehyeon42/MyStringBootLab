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
@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 포함한 생성자
@Builder // 빌더 패턴 사용 가능
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MariaDB/MySQL의 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false) // 제목은 필수 값
    private String title;

    private String author;

    @Column(unique = true) // ISBN은 중복될 수 없는 고유값
    private String isbn;

    private Integer price;

    private LocalDate publishDate;

    /**
     * BookDetail과의 1:1 양방향 연관관계 설정
     * * @OneToOne: 1:1 관계임을 명시
     * mappedBy = "book": 연관관계의 주인이 BookDetail의 'book' 필드임을 지정
     * (DB의 외래 키(FK)는 BookDetail 테이블에 생성)
     * cascade = CascadeType.ALL: 영속성 전이 설정.
     * Book을 저장하거나 삭제할 때 연결된 BookDetail도 함께 저장/삭제
     *fetch = FetchType.LAZY: 지연 로딩 설정.
     * Book을 조회할 때 BookDetail이 바로 필요하지 않다면 실제 사용 시점에 조회하여 성능을 최적화
     */
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BookDetail bookDetail;
}