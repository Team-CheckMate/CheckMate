package org.checkmate.admin.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * 관리자 도서 수정 응답 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.27]
 */
@Getter
@RequiredArgsConstructor
public class BookUpdateResponseDto {
    private final boolean success;
    private final String message;
}
