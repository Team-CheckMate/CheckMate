package org.checkmate.server.dto.request;

import lombok.*;

/**
 * 관리자 도서 등록 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddBookRequestDto {
    String bookTitle;
    String isbn;
    String author;
    String translator;
    String publisher;
    int category_num;
    int l_status = 0;

    public static AddBookRequestDto of( String bookTitle,String isbn,String author,String translator,String publisher,int category_num){
        return new AddBookRequestDto(bookTitle,isbn,author,translator,publisher,category_num,category_num);
    }
}
