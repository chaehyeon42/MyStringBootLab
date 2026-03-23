package com.rookies5.myspringbootLab.controller;

import com.rookies5.myspringbootLab.dto.BookDTO.*; // BookCreateRequest, BookResponse, PatchRequest 등 포함
import com.rookies5.myspringbootLab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    /*
     * POST /api/books : 새 도서 등록
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    /*
     * GET /api/books : 모든 도서 조회
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /*
     * GET /api/books/{id} : ID로 특정 도서 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /*
     * GET /api/books/isbn/{isbn} : ISBN으로 도서 조회
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    /*
     * GET /api/books/search/author?author={author} : 저자로 도서 검색
     */
    @GetMapping("/search/author")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@RequestParam String author) {
        // Service에 해당 메서드가 없다면 추가가 필요할 수 있습니다.
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateRequest request) {
        // 서비스에서 BookUpdateRequest를 처리하는 메서드를 호출합니다.
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    /*
     * PATCH /api/books/{id} : 도서 정보 부분 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> patchUpdateBook(
            @PathVariable Long id,
            @RequestBody PatchRequest request) {
        // 서비스의 updateBook 메서드가 PatchRequest를 받도록 설계되었습니다.
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    /*
     * DELETE /api/books/{id} : 도서 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}