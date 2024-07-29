package org.checkmate.user.service;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadSearchLoanStatusResponseDto;
import org.checkmate.user.mapper.BookMapper;


public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl() {
        this.bookMapper = new BookMapper();
    }

    @Override
    public ObservableList<ReadLoanStatusResponseDto> findAllBooks() throws SQLException {
        return bookMapper.findAllBookLoanStatus();
    }

    @Override
    public CreateBookLoanResponseDto createLoanBook(CreateBookLoanRequestDto requestDto) {
        bookMapper.createLoanBook(requestDto);
        return new CreateBookLoanResponseDto("도서대여를 완료했습니다.");
    }

    @Override
    public ReadSearchLoanStatusResponseDto findByBookName(ReadSearchLoanStatusRequestDto requestDto) throws SQLException {
        String searchName = requestDto.getSearchName();

        return ReadSearchLoanStatusResponseDto.builder().booklist(bookMapper.findByBookName(searchName))
        .build();
    }

}
