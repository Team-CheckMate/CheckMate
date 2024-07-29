package org.checkmate.admin.dto.request;

import lombok.*;

/**
 * 관리자 도서 수정 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookUpdateRequestDto {
    Long bookId;
    String bookTitle;
    String isbn;
    String author;
    String translator;
    String publisher;
    int category_num;
    int l_status;
}
