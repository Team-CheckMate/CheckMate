package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.List;
import org.checkmate.server.dto.response.BookInfoResponseDto;
import org.checkmate.server.entity.Book;

public interface BookService {

    List<BookInfoResponseDto> findAllBooks() throws SQLException;

}
