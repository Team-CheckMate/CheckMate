package org.checkmate.admin.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookCreateResponseDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.admin.mapper.BookManagementMapper;

/**
 * 관리자 도서 서비스 구현 클래스
 * HISTORY1: 관리자 도서 전체조회, 선택 조회, 수정, 삭제 메서드 추가   [이준희  2024.07.27]
 * HISTORY2: 패키지 이동                                      [송헌욱  2024.07.28]
 */
public class BookManagementServiceImpl implements BookManagementService {

    private final BookManagementMapper bookMapper;

    public BookManagementServiceImpl() {
        this.bookMapper = new BookManagementMapper();
    }

    /**
     * [Create] 도서 추가 기능
     * @param requestDto 추가할 도서 정보 요청 객체
     * @return BookCreateResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public BookCreateResponseDto createBook(BookCreateRequestDto requestDto) throws SQLException {
        return bookMapper.addNewBook(requestDto);
    }

    /**
     * [Read] 도서 전체 조회 기능
     * @return BookCreateResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public ObservableList<BookReadLoanStatusResponseDto> readAllBooks() throws SQLException {
        return bookMapper.readAllBooks();
    }

    /**
     * [Read] 선택 도서 수정 시, 해당 도서 정보 응답 기능
     *
     * @param bookId 책 Id
     * @return BookReadInformationResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public BookReadInformationResponseDto readBook(Long bookId) throws SQLException {
        return bookMapper.readBook(bookId);
    }

    /**
     * [Update] 선택 도서 수정 기능
     * @param requestDto 변경할 도서 Id, 요청 객체
     * @return BookUpdateResponseDto 수정된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public BookUpdateResponseDto updateBook(BookUpdateRequestDto requestDto) throws SQLException {
        return bookMapper.updateBook(requestDto);
    }

    /**
     * [Read] 관리자가 도서명을 입력했을때 해당 도서의 정보를 받아오는 기능
     * @Param bookName 해당 도서명
     * @return BookReadLoanStatusResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public ObservableList<BookReadLoanStatusResponseDto> ReadBooksByBookName(String bookName) throws SQLException {
        return bookMapper.ReadBooksByBookName(bookName);
    }

    /**
     * [Delete] 선택 도서 삭제 기능
     *
     * @param bookId 선택된 도서의 Id
     * @return boolean 성공 실패
     * @throws SQLException DataBase 에러
     */
    @Override
    public String deleteSelectedBook(Long bookId) throws SQLException {
        return bookMapper.deleteSelectedBook(bookId);
    }
}
