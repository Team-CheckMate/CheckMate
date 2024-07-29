package org.checkmate.user.service;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadSearchLoanStatusResponseDto;

public interface BookService {

    ObservableList<ReadLoanStatusResponseDto> findAllBooks() throws SQLException;
//    BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto);
    CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto);
    ReadSearchLoanStatusResponseDto findByBookName(ReadSearchLoanStatusRequestDto requestDto) throws SQLException;
}
