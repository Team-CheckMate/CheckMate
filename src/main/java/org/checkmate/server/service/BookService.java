package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.List;

import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;
import org.checkmate.server.entity.BookLoanStatus;

public interface BookService {

    List<BookLoanStatus> findAllBooks() throws SQLException;

    // Admin
    AddBookResponseDto addBook(AddBookRequestDto requestDto) throws SQLException;
}
