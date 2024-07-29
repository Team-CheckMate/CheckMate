package org.checkmate.user.dto.response;

import javafx.collections.ObservableList;
import lombok.Builder;
import lombok.Getter;
/**
 * 도서대여 목록 검색 응답 객체
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
@Getter
@Builder
public class ReadSearchLoanStatusResponseDto {
    private ObservableList<ReadLoanStatusResponseDto> booklist;
}
