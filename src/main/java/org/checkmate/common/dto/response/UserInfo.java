package org.checkmate.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * [ResponseDTO]
 * 1. 로그인 회원에 대한 정보를 담기 위한 객체 - Login Session
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String loginId; // 사원 번호
    private Long teamNo; // team 식별자 번호
    private Long deptNo; // dept 식별자 번호
    private String eName; // 사원 이름
    private String tName; // 팀 이름
    private String dName; // 부서 이름
    private String role; // 권한 (예: ADMIN - 관리자, BASIC - 일반 사원)
    private int delayCnt; // 도서 연체 횟수

}
