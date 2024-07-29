package org.checkmate.admin.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookCreateResponseDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;

public interface BookManagementService {



    ObservableList<BookReadLoanStatusResponseDto> findAllBooksAdmin() throws SQLException;
    BookCreateResponseDto createBook(BookCreateRequestDto requestDto) throws SQLException;
    BookUpdateResponseDto editBook(BookUpdateRequestDto requestDto) throws SQLException;
    boolean deleteSelectedBook(Long bookId) throws SQLException;
    BookReadInformationResponseDto findBook(Long bookId) throws SQLException;
}
