package org.checkmate.server.dto.response;
/**
 * 비밀번호 변경 요청 정보 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.25]
 * HISTORY2: Lombok 적용                           [이준희  2024.07.26]
 */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangePwResponseDto {
    private final boolean success;
    private final String message;
}
