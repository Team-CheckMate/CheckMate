package org.checkmate.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
/**
 * 도서대여 목록 검색 요청 객체
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadSearchLoanStatusRequestDto {
    private String searchName;
}
