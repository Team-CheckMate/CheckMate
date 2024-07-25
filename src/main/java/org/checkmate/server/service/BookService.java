package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.List;
import org.checkmate.server.entity.BookLoanStatus;

public interface BookService {

    List<BookLoanStatus> findAllBooks() throws SQLException;

}
