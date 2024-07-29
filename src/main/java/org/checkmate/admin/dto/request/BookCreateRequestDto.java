package org.checkmate.admin.dto.request;

import lombok.*;

/**
 * 관리자 도서 등록 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookCreateRequestDto {

    private String bookTitle;
    private String isbn;
    private String author;
    private String translator;
    private String publisher;
    private String category;
    private int category_num;

}
