package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.List;

import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.request.EditBookRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;

import javafx.collections.ObservableList;
//import org.checkmate.server.dto.request.BookRentRequestDto;
//import org.checkmate.server.dto.response.BookRentResponseDto;
import org.checkmate.server.dto.response.EditBookResponseDto;
import org.checkmate.server.dto.response.FindAllBooksAdminResponseDto;
import org.checkmate.server.dto.response.FindSelectedBookAdminResponseDto;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.mapper.BookMapper;

/**
 * 도서 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: 도서 등록 기능 추가                    [이준희  2024.07.26]
 * HISTORY3: 관리자 도서 전체조회, 선택 조회, 수정, 삭제 메서드 추가   [이준희  2024.07.27]
 */
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl() {
        this.bookMapper = new BookMapper();
    }

    /**
     * 도서 테이블에 저장된 모든 도서를 조회하는 기능
     * @return List<BookLoanStatus> 도서 모든 정보의 List 컬랙션
     * @throws SQLException DataBase 에러
     */
    @Override
    public ObservableList<BookLoanStatus> findAllBooks() throws SQLException {
        return bookMapper.findAllBookLoanStatus();
    }

//    @Override
//    public BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto) {
//        long memberId = bookRentRequestDto.getMemberId();
//        ObservableList<BookLoanStatus> bookList = bookRentRequestDto.getBookList();
//
//
//
//        int result = bookMapper.addBookLoanRecord(memberId, bookList);
//
//        return new BookRentResponseDto("");
//    }

    /**
     * 관리자가 도서 전체를 조회하는 기능
     * @return AddBookResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public ObservableList<FindAllBooksAdminResponseDto> findAllBooksAdmin() throws SQLException {
        return bookMapper.findAllBookAdmin();
    }

    /**
     * 관리자가 도서를 수정하는 기능
     * @Param bookId, EditBookRequestDto 변경할 도서 Id, 요청 객체
     * @return EditBookResponseDto 수정된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public EditBookResponseDto editBook(EditBookRequestDto requestDto) throws SQLException {
        return bookMapper.editBook(requestDto);
    }

    /**
     * 관리자가 도서를 추가하는 기능
     *  @Param AddBookRequestDto 추가할 도서 정보 요청 객체
     * @return AddBookResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public AddBookResponseDto addBook(AddBookRequestDto requestDto) throws SQLException {
        return bookMapper.addNewBook(requestDto);
    }

    /**
     * 관리자가 선택된 도서를 수정할때 해당 도서의 정보를 받아오는 기능
     * @Param bookId 책 Id
     * @return FindSelectedBookAdminResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public FindSelectedBookAdminResponseDto findBook(Long bookId) throws SQLException {
        return bookMapper.findBook(bookId);
    }

    /**
     * 관리자가 선택된 도서를 삭제하는 기능
     * @Param bookId 선택된 도서의 Id
     * @return booelan 성공 실패
     * @throws SQLException DataBase 에러
     */
    @Override
    public boolean deleteSelectedBook(Long bookId) throws SQLException {
        return bookMapper.deleteSelectedBook(bookId);
    }
}
