package com.rookies5.myspringbootLab.service;

import com.rookies5.myspringbootLab.entity.Book;
import com.rookies5.myspringbootLab.entity.BookDetail;
import com.rookies5.myspringbootLab.exception.BusinessException;
import com.rookies5.myspringbootLab.repository.BookRepository;
import com.rookies5.myspringbootLab.dto.BookDTO.*; // BookCreateRequest, BookResponse, PatchRequest 등 포함
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용을 기본으로 설정 (성능 최적화)
public class BookService {

    private final BookRepository bookRepository;

    /*
     * 저자 이름으로 도서 목록 조회
     */
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /*
     * 새 도서 등록 (C)
     * Book과 BookDetail을 함께 생성하여 연결
     */
    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("이미 존재하는 ISBN입니다.", HttpStatus.BAD_REQUEST);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book)
                    .build();
            book.setBookDetail(detail);
        }

        Book savedBook = bookRepository.save(book);
        return convertToResponse(savedBook);
    }

    /*
     * 모든 도서 조회 (R)
     */
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /*
     * ID로 특정 도서 조회 (R)
     */
    public BookResponse getBookById(Long id) {
        return bookRepository.findByIdWithBookDetail(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new BusinessException("해당 ID의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    /*
     * ISBN으로 도서 조회 (R)
     */
    public BookResponse getBookByIsbn(String isbn) {
        return bookRepository.findByIsbnWithBookDetail(isbn)
                .map(this::convertToResponse)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서가 없습니다.", HttpStatus.NOT_FOUND));
    }

    /*
     *  도서 정보 전체 수정 (PUT 요청 대응)
     * 모든 필드를 입력받은 데이터로 덮어씌움
     */
    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("수정할 도서가 없습니다.", HttpStatus.NOT_FOUND));

        // ISBN 변경 시 중복 체크
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("이미 사용 중인 ISBN입니다.", HttpStatus.BAD_REQUEST);
        }

        // 전체 필드 업데이트
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        // 상세 정보 전체 업데이트
        if (request.getDetailRequest() != null && book.getBookDetail() != null) {
            BookDetail detail = book.getBookDetail();
            BookDetailDTO dReq = request.getDetailRequest();
            detail.setDescription(dReq.getDescription());
            detail.setLanguage(dReq.getLanguage());
            detail.setPageCount(dReq.getPageCount());
            detail.setPublisher(dReq.getPublisher());
            detail.setCoverImageUrl(dReq.getCoverImageUrl());
            detail.setEdition(dReq.getEdition());
        }

        return convertToResponse(book);
    }

    /*
     * 도서 정보 부분 수정 (PATCH 요청 대응)
     * null이 아닌 필드만 선택적으로 업데이트
     */
    @Transactional
    public BookResponse updateBook(Long id, PatchRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("수정할 도서가 없습니다.", HttpStatus.NOT_FOUND));

        if (request.getIsbn() != null && !book.getIsbn().equals(request.getIsbn())) {
            if (bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException("이미 사용 중인 ISBN입니다.", HttpStatus.BAD_REQUEST);
            }
            book.setIsbn(request.getIsbn());
        }

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getPrice() != null) book.setPrice(request.getPrice());
        if (request.getPublishDate() != null) book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null && book.getBookDetail() != null) {
            BookDetail detail = book.getBookDetail();
            BookDetailPatchRequest dReq = request.getDetailRequest();
            if (dReq.getDescription() != null) detail.setDescription(dReq.getDescription());
            if (dReq.getLanguage() != null) detail.setLanguage(dReq.getLanguage());
            if (dReq.getPageCount() != null) detail.setPageCount(dReq.getPageCount());
            if (dReq.getPublisher() != null) detail.setPublisher(dReq.getPublisher());
            if (dReq.getCoverImageUrl() != null) detail.setCoverImageUrl(dReq.getCoverImageUrl());
            if (dReq.getEdition() != null) detail.setEdition(dReq.getEdition());
        }

        return convertToResponse(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("삭제할 도서가 없습니다.", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }

    /*
     * Entity -> Response DTO 변환
     */
    private BookResponse convertToResponse(Book book) {
        BookResponse response = BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publishDate(book.getPublishDate())
                .build();

        if (book.getBookDetail() != null) {
            response.setDetailResponse(BookDetailResponse.builder()
                    .description(book.getBookDetail().getDescription())
                    .language(book.getBookDetail().getLanguage())
                    .pageCount(book.getBookDetail().getPageCount())
                    .publisher(book.getBookDetail().getPublisher())
                    .coverImageUrl(book.getBookDetail().getCoverImageUrl())
                    .edition(book.getBookDetail().getEdition())
                    .build());
        }
        return response;
    }
}