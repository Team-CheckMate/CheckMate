package org.checkmate.user.service;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.request.ReqLoginIdAndTeamId;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadSearchLoanStatusResponseDto;
import org.checkmate.user.dto.response.TeamMemberLoanStatusDegree;
import org.checkmate.user.dto.response.TeamMemberLoanStatusForView;
import org.checkmate.user.mapper.BookMapper;


public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl() {
        this.bookMapper = new BookMapper();
    }

    @Override
    public ObservableList<ReadLoanStatusResponseDto> findAllBooks() throws SQLException {
        return bookMapper.findAllBookLoanStatus();
    }

    @Override
    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) {
        bookMapper.createLoanBook(requestDto);
        return new CreateBookLoanResponseDto("도서대여를 완료했습니다.");
    }

    @Override
    public ReadSearchLoanStatusResponseDto findByBookName(ReadSearchLoanStatusRequestDto requestDto)
            throws SQLException {
        String searchName = requestDto.getSearchName();

        return ReadSearchLoanStatusResponseDto.builder()
                .booklist(bookMapper.findByBookName(searchName))
                .build();
    }

    /**
     * [Read] 팀 구성원 대여 현황 조회 Business logic
     *
     * @param requestDto ReqLoginIdAndTeamId 요청 객체
     * @return TeamMemberLoanStatusForView
     */
    @Override
    public TeamMemberLoanStatusForView findByTeamMemberLoanStatus(ReqLoginIdAndTeamId requestDto) {
        int totalLoanBook = 0;
        int totalLastMonthLoanBook = 0;
        int totalLastYearBook = 0;
        List<TeamMemberLoanStatusDegree> record = bookMapper.findTeamMemberLoanStatus(requestDto.getTeamId());

        for (TeamMemberLoanStatusDegree dto : record) {
            totalLoanBook += dto.getBookCount();
            totalLastMonthLoanBook += dto.getLastMonthCount();
            totalLastYearBook += dto.getLastYearCount();
        }

        return TeamMemberLoanStatusForView.builder()
                .totalLoanBook(totalLoanBook)
                .totalLastMonthLoanBook(totalLastMonthLoanBook)
                .totalLastYearBook(totalLastYearBook)
                .list(record)
                .build();
    }
}
