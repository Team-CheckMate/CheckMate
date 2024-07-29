package org.checkmate.user.dto.request;

import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
/**
 * 도서대여 등록 요청 객체
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBookLoanRequestDto {
    private String loginId;
    private ObservableList<ReadLoanStatusResponseDto> bookList;

}
