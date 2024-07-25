package org.checkmate.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.checkmate.server.entity.Book;
import org.checkmate.server.util.TypeFormatter;

/**
 * 책 정보 응답 객체
 * HISTORY1: 최초 생성                                         [송헌욱  2024.07.25]
 */
@Getter
@ToString
@Builder
@AllArgsConstructor
public class BookInfoResponseDto {

    private Long bookId; // 고유 식별자
    private String ISBN; // ISBN
    private String bName; // 책이름
    private String author; // 저자
    private String translator; // 엮은이
    private String publisher; // 출판사
    private Long categoryId; // 책 분류 식별자(FK)
    private Boolean lStatus; // 대출 가능 상태

    public static BookInfoResponseDto from(Book book) {
        return BookInfoResponseDto.builder()
                .bookId(book.getBookId())
                .ISBN(book.getISBN())
                .bName(book.getBName())
                .author(book.getAuthor())
                .translator(book.getTranslator())
                .publisher(book.getPublisher())
                .categoryId(book.getCategoryId())
                .lStatus(TypeFormatter.IntegerToBoolean(book.getLStatus()))
                .build();
    }

}
