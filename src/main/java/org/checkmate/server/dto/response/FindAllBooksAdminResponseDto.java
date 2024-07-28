package org.checkmate.server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Date;

/**
 * 관리자 전체 도서 조회 응답 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.27]
 */

@Getter
@ToString
@Builder
public class FindAllBooksAdminResponseDto {
    private Long bookId; // 고유 식별자
    private String bName; // 책이름
    private String ISBN; // ISBN
    private String author; // 저자
    private String publisher; // 출판사
    private String lStatus; // 대출 가능 상태
    private Date addDate; // 반납 예정 날짜
    private String eName; // 대출자

}
