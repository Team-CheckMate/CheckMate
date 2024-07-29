package org.checkmate.admin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@ToString
@Builder
public class ReadBookLoanRecordsResponseDto {
    private Long blrId; // 고유 식별자
    private String loginId; // 사원 ID
    private String eName; // 사원 이름
    private String dName; // 부서 이름
    private String tName; // 팀 이름
    private String bName; // 대여 책 제목
    private Date loanDate; // 대여 날짜
    private Date returnPreDate; // 반납 예정일
    private Date returnDate; // 반납 날짜
}
