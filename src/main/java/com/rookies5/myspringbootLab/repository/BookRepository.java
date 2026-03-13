package com.rookies5.myspringbootLab.repository;

import com.rookies5.myspringbootLab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * DB 접근을 위한 인터페이스
 * JpaRepository<엔티티타입, PK타입>을 상속받으면 기본적인 CRUD가 자동 생성됨
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    // 쿼리 메소드: ISBN으로 도서 한 권 찾기
    // 결과가 없을 수도 있으므로 Optional로 감싸서 반환
    Optional<Book> findByIsbn(String isbn);

    // 쿼리 메소드: 특정 저자의 모든 도서 찾기
    List<Book> findByAuthor(String author);
}