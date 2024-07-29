package org.checkmate.admin.controller.server;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

public class BookController {

    private final BookManagementService bookService;

    public BookController() {
        this.bookService = new BookManagementServiceImpl();
    }

    public ObservableList<BookReadLoanStatusResponseDto> readAllBooks() throws SQLException{
        return bookService.readAllBooks();
    }

    public void createBook(BookCreateRequestDto requestDto) throws SQLException {
        bookService.createBook(requestDto);
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
    }

    public void updateBook(BookUpdateRequestDto requestDto) throws SQLException{
        bookService.updateBook(requestDto);
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
    }

    public String deleteSelectedBook(Long bookId) throws SQLException{
        return bookService.deleteSelectedBook(bookId);
    }

    public BookReadInformationResponseDto readBook(Long bookId) throws SQLException {
        return bookService.readBook(bookId);
    }

    public ObservableList<BookReadLoanStatusResponseDto> ReadBooksByBookName(String bookName) throws SQLException{
        return bookService.readBooksByBookName(bookName);
    }






}
