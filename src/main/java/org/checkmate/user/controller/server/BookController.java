package org.checkmate.user.controller.server;

import java.sql.SQLException;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReqLoginIdAndTeamId;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.TeamMemberLoanStatusForView;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookServiceImpl();
    }

    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) throws SQLException {
        return bookService.createLoanBook(requestDto);
    }

    /**
     * [Read] 팀 구성원 대여 현황 조회 Server Controller
     * @param loginId 사원 번호: View
     * @param teamId 팀 식별자
     * @return [ResponseDTO] TeamMemberLoanStatusForView - 서비스 로직을 수행한 이후 요청에 대한 데이터 응답 객체
     * @throws SQLException Database 예외
     */
    public TeamMemberLoanStatusForView teamMemberLoanStatus(String loginId, Long teamId) {
        ReqLoginIdAndTeamId requestDto = ReqLoginIdAndTeamId.builder()
                .loginId(loginId)
                .teamId(teamId)
                .build();
        return bookService.findByTeamMemberLoanStatus(requestDto);
    }
}
