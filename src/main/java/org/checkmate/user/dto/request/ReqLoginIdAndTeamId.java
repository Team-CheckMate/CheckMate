package org.checkmate.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * [RequestDTO]
 * 1. [Read] 팀 구성원 대여 현황 조회 Server Controller 에서 요청 객체로 사용
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReqLoginIdAndTeamId {

    private String loginId; // 사원 번호
    private Long teamId; // 팀 식별자

}
