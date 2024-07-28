package org.checkmate.user.service;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;

public interface BookService {

    ObservableList<ReadLoanStatusResponseDto> findAllBooks() throws SQLException;
//    BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto);

}
