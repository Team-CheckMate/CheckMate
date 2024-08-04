package org.checkmate.user.service;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.request.ReqLoginIdAndTeamNo;
import org.checkmate.user.dto.response.*;
import org.checkmate.user.mapper.BookMapper;

@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper = new BookMapper();

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
     * @param requestDto ReqLoginIdAndTeamNo 요청 객체
     * @return TeamMemberLoanStatusForView
     */
    @Override
    public TeamMemberLoanStatusForView findByTeamMemberLoanStatus(ReqLoginIdAndTeamNo requestDto) {
        int totalLoanBook = 0;
        int totalLastMonthLoanBook = 0;
        int totalLastYearBook = 0;

        log.info(" <<< [ 📢 BookMapper를 호출하여 처리 시작: teamNo = {} ]", requestDto.getTeamNo());
        List<TeamMemberLoanStatusDegree> record = bookMapper.findTeamMemberLoanStatus(
                requestDto.getTeamNo());
        log.info(" >>> [ ✅ Team Member Loan Status 조회 완료. 총 {}개의 레코드를 가져왔습니다. ]", record.size());

        for (TeamMemberLoanStatusDegree dto : record) {
            log.debug(" <<< [ 📝 현재 레코드 처리: loginId = {}, bookCount = {} ]", dto.getLoginId(), dto.getBookCount());
            totalLoanBook += dto.getBookCount();
            totalLastMonthLoanBook += dto.getLastMonthCount();
            totalLastYearBook += dto.getLastYearCount();
        }

        log.info(" >>> [ ✅ 모든 레코드 처리 완료. 총 대출 책 수: {}, 지난 달 대출 책 수: {}, 지난 해 대출 책 수: {} ]",
                totalLoanBook, totalLastMonthLoanBook, totalLastYearBook);

        TeamMemberLoanStatusForView result = TeamMemberLoanStatusForView.builder()
                .totalLoanBook(totalLoanBook)
                .totalLastMonthLoanBook(totalLastMonthLoanBook)
                .totalLastYearBook(totalLastYearBook)
                .list(record)
                .build();
        log.info(" >>> [ ✅ TeamMemberLoanStatusForView 객체 생성 완료 ]");
        return result;
    }

    @Override
    public ObservableList<ReadLoanStatusResponseDto> findLoanBookNotReturnByLoginId(
            String loginId) {
        return bookMapper.findLoanBookNotReturnByLoginId(loginId);
    }

    @Override
    public void updateReturnLoanBook(ObservableList<ReadLoanStatusResponseDto> selectedBooks) {
        bookMapper.updateReturnLoanBook(selectedBooks);
    }

    @Override
    public void updateReturnOverdueLoanBook(String loginId,
            ObservableList<ReadLoanStatusResponseDto> selectedBooks) {
        bookMapper.updateReturnOverdueLoanBook(loginId, selectedBooks);
    }

    @Override
    public ObservableList<ReadLoanStatusResponseDto> findAllReadMyBooks(String loginId) {
        return bookMapper.findAllReadMyBooks(loginId);
    }

    @Override
    public int getOverdueBookCount(String loginId) {
        return bookMapper.getOverdueBookCount(loginId);
    }

    @Override
    public ObservableList<ReadLoanStatusResponseDto> findOverdueLoanBook(String loginId) {
        return bookMapper.findOverdueLoanBook(loginId);
    }

    @Override
    public boolean createBookRequest(String loginId, String bName, String publisher,
            String author) {
        return bookMapper.createBookRequest(loginId, bName, publisher, author);
    }

    @Override
    public ObservableList<ReadBookRequestResponseDto> findAllBookRequest(long deptNo) {
        return bookMapper.findAllBookRequest(deptNo);
    }

}
