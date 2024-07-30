package org.checkmate.user.controller.server;

import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadMyDepartmentBookStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadMyBookDeptStatusResDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.sql.SQLException;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookServiceImpl();
    }

    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) throws SQLException {
        return bookService.createLoanBook(requestDto);
    }

    /**
     * 팀 구성원 대여 현황
     * @param loginId 사원 번호: View
     * @param teamId 팀 식별자
     * @return ReadMyBookDeptStatusResDto - 서비스 로직을 수행한 이후 요청에 대한 데이터 응답 객체
     * @throws SQLException Database 예외
     */
    public ReadMyBookDeptStatusResDto readTeamMemberLendingStatus(String loginId, Long teamId) throws SQLException {
        ReadMyDepartmentBookStatusRequestDto requestDto = ReadMyDepartmentBookStatusRequestDto.builder()
                .loginId(loginId)
                .teamId(teamId)
                .build();
        return bookService.findMyDepartmentStatus(requestDto);
    }
}
