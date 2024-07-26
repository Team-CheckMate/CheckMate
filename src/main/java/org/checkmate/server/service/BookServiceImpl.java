package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.List;

import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.mapper.BookMapper;

/**
 * 도서 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: 도서 등록 기능 추가                      [이준희  2024.07.26]
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
    public List<BookLoanStatus> findAllBooks() throws SQLException {
        return bookMapper.findAllBookLoanStatus();
    }

    /**
     * 관리자가 도서를 추가하는 기능
     * @return AddBookResponseDto 등록된 도서 정보 결과
     * @throws SQLException DataBase 에러
     */
    @Override
    public AddBookResponseDto addBook(AddBookRequestDto requestDto) throws SQLException {
        return bookMapper.addNewBook(requestDto);
    }
}
