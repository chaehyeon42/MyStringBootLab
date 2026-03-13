package com.rookies5.myspringbootLab.controller;

import com.rookies5.myspringbootLab.entity.Book;
import com.rookies5.myspringbootLab.exception.BusinessException;
import com.rookies5.myspringbootLab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor // Repository 주입을 위해 사용합니다.
public class BookRestController {

    private final BookRepository bookRepository;

    //모든 도서 조회
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //ID로 특정 도서 조회 (404 처리 포함)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    //ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서가 없습니다.", HttpStatus.NOT_FOUND));
    }

    //새 도서 등록
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("수정할 도서가 없습니다.", HttpStatus.NOT_FOUND));
        
        // 데이터 업데이트 (엔티티에 Setter가 있거나 메소드가 있어야 함)
        // 예: book.updateDetails(bookDetails);
        
        return ResponseEntity.ok(bookRepository.save(book));
    }

    // 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("삭제할 도서가 없습니다.", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
        return ResponseEntity.noContent().build();
    }
}