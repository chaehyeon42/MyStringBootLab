package com.rookies5.myspringbootLab;

import com.rookies5.myspringbootLab.entity.Book;
import com.rookies5.myspringbootLab.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB(MariaDB) 사용
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() {
        //도서 등록 테스트
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    public void testFindByIsbn() {
        // ISBN으로 조회 테스트 (데이터가 먼저 있어야 함)
        testCreateBook(); // 샘플 데이터 등록
        Book found = bookRepository.findByIsbn("9788956746425").orElseThrow();
        assertThat(found.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    public void testFindByAuthor() {
        //저자명으로 조회 테스트
        testCreateBook();
        List<Book> books = bookRepository.findByAuthor("홍길동");
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    public void testUpdateBook() {
        // 4. 정보 수정 테스트
        testCreateBook();
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow();
        book.setPrice(32000); // 가격 변경
        Book updated = bookRepository.save(book);
        assertThat(updated.getPrice()).isEqualTo(32000);
    }

    @Test
    public void testDeleteBook() {
        //삭제 테스트
        testCreateBook();
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow();
        bookRepository.delete(book);
        assertThat(bookRepository.findByIsbn("9788956746425")).isEmpty();
    }
}