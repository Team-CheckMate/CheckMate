package org.checkmate.user.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [ResponseDTO]
 * 1. [Read] 팀 구성원 대여 현황을 Business 로직을 수행한 이후 View 로 전달 - findByTeamMemberLoanStatus
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberLoanStatusForView {

    private int totalLoanBook;
    private int totalLastMonthLoanBook;
    private int totalLastYearBook;
    private List<TeamMemberLoanStatusDegree> list;
}
