package org.checkmate.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 비밀번호 변경 요청 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.24]
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePasswordRequestDto {

    private String loginId;
    private String nowPw;
    private String changePw;

}
