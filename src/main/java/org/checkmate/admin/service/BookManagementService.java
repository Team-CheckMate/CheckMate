package org.checkmate.admin.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.*;

public interface BookManagementService {



    ObservableList<BookReadLoanStatusResponseDto> readAllBooks() throws SQLException;
    BookCreateResponseDto createBook(BookCreateRequestDto requestDto) throws SQLException;
    BookUpdateResponseDto updateBook(BookUpdateRequestDto requestDto) throws SQLException;
    String deleteSelectedBook(Long bookId) throws SQLException;
    BookReadInformationResponseDto readBook(Long bookId) throws SQLException;
    ObservableList<BookReadLoanStatusResponseDto> readBooksByBookName(String bookName) throws SQLException;
    ObservableList<ReadBookLoanRecordsResponseDto> readAllBookLoanRecordsAdmin() throws SQLException;

    ObservableList<ReadBookLoanRecordsForChartResponseDto> readDepartmentsBookLoanRecords() throws SQLException;
    ObservableList<ReadBookLoanRecordsForChartResponseDto> readTeamsBookLoanRecords() throws SQLException;
}
