package org.checkmate.user.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.request.ReqLoginIdAndTeamNo;
import org.checkmate.user.dto.response.*;

public interface BookService {

    ObservableList<ReadLoanStatusResponseDto> findAllBooks() throws SQLException;
    //    BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto);
    CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto);
    ReadSearchLoanStatusResponseDto findByBookName(ReadSearchLoanStatusRequestDto requestDto) throws SQLException;
    TeamMemberLoanStatusForView findByTeamMemberLoanStatus(ReqLoginIdAndTeamNo requestDto);
    ObservableList<ReadLoanStatusResponseDto> findLoanBookNotReturnByLoginId(String loginId);
    void updateReturnLoanBook(ObservableList<ReadLoanStatusResponseDto> selectedBooks);
    void updateReturnOverdueLoanBook(String loginId, ObservableList<ReadLoanStatusResponseDto> selectedBooks);
    ObservableList<ReadLoanStatusResponseDto> findAllReadMyBooks(String loginId);
    int getOverdueBookCount(String loginId);
    ObservableList<ReadLoanStatusResponseDto> findOverdueLoanBook(String loginId);
    boolean createBookRequest(String loginId, String bName, String publisher, String author);
    ObservableList<ReadBookRequestResponseDto> findAllBookRequest(long deptNo);
}
