package org.checkmate.server.service;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import org.checkmate.server.dto.request.BookRentRequestDto;
import org.checkmate.server.dto.response.BookRentResponseDto;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.mapper.BookMapper;

/**
 * 도서 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
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

    @Override
    public BookRentResponseDto bookRent(BookRentRequestDto bookRentRequestDto) {
        long memberId = bookRentRequestDto.getMemberId();
        ObservableList<BookLoanStatus> bookList = bookRentRequestDto.getBookList();



        int result = bookMapper.addBookLoanRecord(memberId, bookList);

        return new BookRentResponseDto("");
    }


}
