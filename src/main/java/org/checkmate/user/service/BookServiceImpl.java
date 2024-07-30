package org.checkmate.user.service;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadMyDepartmentBookStatusRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadMyBookDeptStatusResDto;
import org.checkmate.user.dto.response.ReadMyDepartmentBookStatusResponseDto;
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

    @Override
    public ReadMyBookDeptStatusResDto findMyDepartmentStatus(
            ReadMyDepartmentBookStatusRequestDto requestDto) throws SQLException {
        int totalLoanBook = 0;
        int totalLastMonthLoanBook = 0;
        int totalLastYearBook = 0;

        List<ReadMyDepartmentBookStatusResponseDto> search =
                bookMapper.findMyDepartmentBookLoanStatus(requestDto.getLoginId(), requestDto.getTeamId());

        for (ReadMyDepartmentBookStatusResponseDto dto : search) {
            totalLoanBook += dto.getBookCount();
            totalLastMonthLoanBook += dto.getLastMonthCount();
            totalLastYearBook += dto.getLastYearCount();
        }

        return ReadMyBookDeptStatusResDto.builder()
                .totalLoanBook(totalLoanBook)
                .totalLastMonthLoanBook(totalLastMonthLoanBook)
                .totalLastYearBook(totalLastYearBook)
                .list(search)
                .build();
    }
}
