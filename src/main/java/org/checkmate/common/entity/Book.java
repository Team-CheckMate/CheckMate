package org.checkmate.common.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 객체
 * HISTORY1: 최초 생성      [송헌욱  2024.07.25]
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    private Long bookId; // 고유 식별자
    private String ISBN; // ISBN
    private String bName; // 책이름
    private String author; // 저자
    private String translator; // 엮은이
    private String publisher; // 출판사
    private Long categoryId; // 책 분류 식별자(FK)
    private Integer lStatus; // 대출 가능 상태(0 = false, 1 = true)
    private LocalDateTime addDate; // 도서 등록 날자

    @Builder
    public Book(Long bookId, String ISBN, String bName, String author, String translator,
            String publisher, Long categoryId, Integer lStatus, LocalDateTime addDate) {
        this.bookId = bookId;
        this.ISBN = ISBN;
        this.bName = bName;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.categoryId = categoryId;
        this.lStatus = lStatus;
        this.addDate = addDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Book book = (Book) object;
        return Objects.equals(bookId, book.bookId) && Objects.equals(ISBN,
                book.ISBN) && Objects.equals(bName, book.bName) && Objects.equals(
                author, book.author) && Objects.equals(translator, book.translator)
                && Objects.equals(publisher, book.publisher) && Objects.equals(
                categoryId, book.categoryId) && Objects.equals(lStatus, book.lStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, ISBN, bName, author, translator, publisher, categoryId,
                lStatus);
    }
}
