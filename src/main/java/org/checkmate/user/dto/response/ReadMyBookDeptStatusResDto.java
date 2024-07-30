package org.checkmate.user.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadMyBookDeptStatusResDto {

    private int totalLoanBook;
    private int totalLastMonthLoanBook;
    private int totalLastYearBook;
    private List<ReadMyDepartmentBookStatusResponseDto> list;
}
