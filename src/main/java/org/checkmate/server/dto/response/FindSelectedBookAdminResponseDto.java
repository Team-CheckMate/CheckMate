package org.checkmate.server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 관리자 선택 도서 조회 응답 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.27]
 */

@Getter
@ToString
@Builder
public class FindSelectedBookAdminResponseDto {
    private String bName; // 책이름
    private String publisher; // 출판사
    private String isbn; // ISBN
    private String author; // 저자
    private String translator; // 옮긴이
    private int categoryId; // 카테고리 번호
    private String cName; // 카테고리 이름
    private Boolean lStatus; // 대출가능 상태
}