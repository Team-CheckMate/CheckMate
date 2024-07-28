package org.checkmate.server.dto.request;

import lombok.*;

/**
 * 관리자 도서 수정 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditBookRequestDto {
    Long bookId;
    String bookTitle;
    String isbn;
    String author;
    String translator;
    String publisher;
    int category_num;
    int l_status;


    public static EditBookRequestDto of(Long bookId,String bookTitle, String isbn, String author, String translator, String publisher, int category_num,int l_status){
        return new EditBookRequestDto(bookId,bookTitle,isbn,author,translator,publisher,category_num,l_status);
    }
}
