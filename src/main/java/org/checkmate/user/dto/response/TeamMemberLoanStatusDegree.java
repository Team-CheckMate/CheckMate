package org.checkmate.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [ResponseDTO]
 * 1. Mapper 요청에 맞는 Database 응답 객체 Degree = Field
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberLoanStatusDegree {

    private String loginId; // 로그인 아이디
    private String eName; // 사원 이름
    private int bookCount; // 현재 대여 책 수
    private int currentMonthCount; // 이번 달 읽은 책 수 (반납 수)
    private int lastMonthCount; // 지난 달 읽은 책 수 (반납 수)
    private int lastYearCount; // 지난 년도 읽은 책 수 (반납 수)

}
