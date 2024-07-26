package org.checkmate.server.service;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import org.checkmate.server.dto.request.BookRentRequestDto;
import org.checkmate.server.dto.response.BookRentResponseDto;
import org.checkmate.server.entity.BookLoanStatus;

public interface BookService {

    ObservableList<BookLoanStatus> findAllBooks() throws SQLException;
    BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto);
}
