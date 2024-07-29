package org.checkmate.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * 도서 대여 등록 응답 객체
 * HISTORY1: 최초 생성                                         [권혁규  2024.07.29]
 */
@Getter
@RequiredArgsConstructor
public class CreateBookLoanResponseDto {
    private final String message;
}
