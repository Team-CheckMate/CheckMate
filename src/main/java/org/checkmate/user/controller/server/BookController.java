package org.checkmate.user.controller.server;

import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.sql.SQLException;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookServiceImpl();
    }

    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) throws SQLException {
        return bookService.createLoanBook(requestDto);
    }
}
