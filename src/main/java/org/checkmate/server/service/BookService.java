package org.checkmate.server.service;

import java.sql.SQLException;
//import java.util.List;

import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.request.EditBookRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;

import javafx.collections.ObservableList;
//import org.checkmate.server.dto.request.BookRentRequestDto;
//import org.checkmate.server.dto.response.BookRentResponseDto;
import org.checkmate.server.dto.response.EditBookResponseDto;
import org.checkmate.server.dto.response.FindAllBooksAdminResponseDto;
import org.checkmate.server.dto.response.FindSelectedBookAdminResponseDto;
import org.checkmate.server.entity.BookLoanStatus;

public interface BookService {

    ObservableList<BookLoanStatus> findAllBooks() throws SQLException;
//    BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto);

    // Admin
    ObservableList<FindAllBooksAdminResponseDto> findAllBooksAdmin() throws SQLException;
    AddBookResponseDto addBook(AddBookRequestDto requestDto) throws SQLException;
    EditBookResponseDto editBook(EditBookRequestDto requestDto) throws SQLException;
    boolean deleteSelectedBook(Long bookId) throws SQLException;

    FindSelectedBookAdminResponseDto findBook(Long bookId) throws SQLException;

}
