package org.checkmate.admin.controller.server;

import java.sql.SQLException;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;

public class BookController {

    private final BookManagementService bookService;

    public BookController() {
        this.bookService = new BookManagementServiceImpl();
    }

    public void createBook(BookCreateRequestDto requestDto) throws SQLException {
        bookService.createBook(requestDto);
    }
}
