package org.checkmate.user.controller.server;

import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReqLoginIdAndTeamNo;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.TeamMemberLoanStatusForView;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

@Log4j2
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService = new BookServiceImpl();;

    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) throws SQLException {
        return bookService.createLoanBook(requestDto);
    }

    /**
     * [Read] 팀 구성원 대여 현황 조회 Server Controller
     * @param loginId 사원 번호: View
     * @param teamNo 팀 식별자
     * @return [ResponseDTO] TeamMemberLoanStatusForView - 서비스 로직을 수행한 이후 요청에 대한 데이터 응답 객체
     */
    public CommonResponse<TeamMemberLoanStatusForView> teamMemberLoanStatus(String loginId, Long teamNo) {
        log.info(" >>> [ ✨ 요청 DTO 생성 시작: loginId = \"{}\", teamNo = \"{}\" ]", loginId, teamNo);

        ReqLoginIdAndTeamNo requestDto = ReqLoginIdAndTeamNo.builder()
                .loginId(loginId)
                .teamNo(teamNo)
                .build();
        log.info(" >>> [ ✅ 요청 DTO 생성 완료 ]" );

        log.info(" <<< [ 📢 BookService 호출: 요청 DTO 전달 ]");
        TeamMemberLoanStatusForView data = bookService.findByTeamMemberLoanStatus(requestDto);
        log.info(" >>> [ ✅ TeamMemberLoanStatus 메서드 호출 성공 ]");
        log.info(" >>> [ ✅ Successful TeamMemberLoanStatus Method ]");

        CommonResponse<TeamMemberLoanStatusForView> response = CommonResponse.success("팀 구성원 대여 현황 조회 성공", data);
        log.info(" >>> [ 📤 응답 객체 생성 완료 ]");
        return response;
    }
}
