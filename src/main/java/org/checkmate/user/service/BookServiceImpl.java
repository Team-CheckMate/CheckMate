package org.checkmate.user.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
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

}
