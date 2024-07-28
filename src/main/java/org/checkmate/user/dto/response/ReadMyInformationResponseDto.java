package org.checkmate.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 마이페이지 정보 응답 객체
 * HISTORY1: 최초 생성                                         [이준희  2024.07.26]
 */

@Getter
@AllArgsConstructor
public class ReadMyInformationResponseDto {
    private final String loginId;
    private final String TeamName;
}
