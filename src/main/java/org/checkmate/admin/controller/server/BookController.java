package org.checkmate.admin.controller.server;

import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import org.checkmate.admin.controller.view.BookUpdatePageController;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsForChartResponseDto;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

public class BookController {

    private final BookManagementService bookService;

    public BookController() {
        this.bookService = new BookManagementServiceImpl();
    }

    /**
     * [Read] 모든 대여 현황 조회 Server Controller
     * @return [ResponseDTO] BookReadLoanStatusResponseDto - 서비스 로직을 수행한 이후 요청에 대한 데이터 응답 객체
     * @throws SQLException Database 예외
     */
    public List<BookReadLoanStatusResponseDto> readAllBooks() throws SQLException{
        return bookService.readAllBooks();
    }

    /**
     * [Create] 도서 추가 Server Controller
     * @return void 성공
     * @throws SQLException Database 예외
     */
    public void createBook(String title,String isbnText,String authorText,String translatorText,String publisherText,String category,int categoryNum ) throws SQLException {
        BookCreateRequestDto requestDto = BookCreateRequestDto.builder()
            .bookTitle(title)
            .isbn(isbnText)
            .author(authorText)
            .translator(translatorText)
            .publisher(publisherText)
            .category(category)
            .category_num(categoryNum)
            .build();
        bookService.createBook(requestDto);
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
    }

    /**
     * [Update] 도서 수정 Server Controller
     * @return void 성공
     * @throws SQLException Database 예외
     */
    public void updateBook(Long bookId,String bookTitle,String isbn,String author,String translator,String publisher,int lStatusInt,int category_num) throws SQLException{
        BookUpdateRequestDto requestDto = BookUpdateRequestDto.builder()
                .bookId(bookId)
                .bookTitle(bookTitle)
                .isbn(isbn)
                .author(author)
                .translator(translator)
                .publisher(publisher)
                .l_status(lStatusInt)
                .category_num(category_num)
                .build();
        bookService.updateBook(requestDto);
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
    }

    /**
     * [Delete] 도서 수정 Server Controller
     * @return String msg
     * @throws SQLException Database 예외
     */

    public String deleteSelectedBook(Long bookId) throws SQLException{
        return bookService.deleteSelectedBook(bookId);
    }

    /**
     * [Read] 도서 정보 확인 Server Controller
     * @return BookReadInformationResponseDto 응답객체
     * @throws SQLException Database 예외
     */
    public BookReadInformationResponseDto readBook(Long bookId) throws SQLException {
        return bookService.readBook(bookId);
    }

    /**
     * [Read] 도서 정보 확인 by 이름 검색 Server Controller
     * @return List 응답객체
     * @throws SQLException Database 예외
     */
    public List<BookReadLoanStatusResponseDto> ReadBooksByBookName(String bookName) throws SQLException{
        return bookService.readBooksByBookName(bookName);
    }

    public List<ReadBookLoanRecordsResponseDto> readAllBookLoanRecordsAdmin() throws SQLException{
        return bookService.readAllBookLoanRecordsAdmin();
    }

    public void readDepartmentsBookLoanRecords() throws SQLException{
        bookService.readPivotDepartmentsBookLoanRecords();
    }
    public List<ReadBookLoanRecordsForChartResponseDto> readTeamsBookLoanRecords() throws SQLException{
        return bookService.readTeamsBookLoanRecords();
    }

    public List<ReadBookLoanRecordsResponseDto> readBookLoanRecordByNameAdmin(String eName) throws SQLException{
        return bookService.readBookLoanRecordByNameAdmin(eName);
    }

    public String deleteSelectedBookLoanRecord(Long bookId) throws SQLException{
        return bookService.deleteSelectedBookLoanRecord(bookId);
    }

    public String update_return_date(Long blrId) throws SQLException{
        return bookService.update_return_date(blrId);
    }
}
