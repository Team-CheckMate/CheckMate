package org.checkmate.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 나의 부서 도서 현황 응답 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.25]
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadMyDepartmentBookStatusResponseDto {

    private String loginId; // 로그인 아이디
    private String eName; // 사원 이름
    private int bookCount; // 현재 대여 책 수
    private int currentMonthCount; // 이번 달 읽은 책 수 (반납 수)
    private int lastMonthCount; // 지난 달 읽은 책 수 (반납 수)
    private int lastYearCount; // 지난 년도 읽은 책 수 (반납 수)
}
