package com.rookies5.myspringbootLab.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * 도서 관련 데이터 전송 객체 (DTO) 모음
 * 이미지 4번, 7번의 클래스 다이어그램 구조를 반영했습니다.
 */
public class BookDTO {

    // 1. 도서 생성 요청 (POST)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookCreateRequest {
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // 상세 정보 동시 등록을 위한 필드 추가 (이미지 4번 Request)
        private BookDetailDTO detailRequest;
    }

    // 2. 도서 전체 수정 요청 (PUT)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookUpdateRequest {
        private String title;
        private String author;
        private String isbn; // 수정 시 ISBN도 포함될 수 있음
        private Integer price;
        private LocalDate publishDate;
        private BookDetailDTO detailRequest;
    }

    // 3. 도서 부분 수정 요청 (PATCH) - [컨트롤러 에러 해결 포인트!]
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PatchRequest {
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // 상세 정보 부분 수정을 위한 필드 추가 (이미지 5번)
        private BookDetailPatchRequest detailRequest;
    }

    // 4. 상세 정보 등록/수정용 DTO (이미지 4번 BookDetailDTO)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookDetailDTO {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    // 5. 상세 정보 부분 수정 전용 DTO (이미지 5번)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookDetailPatchRequest {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    // 6. 도서 정보 응답 (Response)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // 상세 정보 응답 포함 (이미지 4번 Response)
        private BookDetailResponse detailResponse;
    }

    // 7. 상세 정보 응답 전용 DTO (이미지 4번 BookDetailResponse)
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookDetailResponse {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }
}