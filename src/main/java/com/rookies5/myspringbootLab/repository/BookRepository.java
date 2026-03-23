package com.rookies5.myspringbootLab.repository;

import com.rookies5.myspringbootLab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /*
    *중복 ISBN 확인을 위한 메서드
    */
    // 쿼리 메소드: 특정 저자의 모든 도서 찾기
    List<Book> findByAuthor(String author);
    boolean existsByIsbn(String isbn);

    /*
    *Fetch Join을 이용한 상세 정보 포함 조회
    * @Query: JPQL을 사용하여 직접 쿼리를 작성
    * JOIN FETCH: 지연 로딩(LAZY) 설정인 BookDetail을 한 번의 쿼리(Join)로 함께 가져와서
    * N+1 문제를 방지하고 성능을 최적화
    */
    // 쿼리 메소드: 특정 저자의 모든 도서 찾기
    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    /*
     *  ISBN으로 상세 정보 포함 조회
     */
    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);
}