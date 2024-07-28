package org.checkmate.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 로그인 요청 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY3: Lombok 적용                           [송헌욱  2024.07.25]
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String loginId;  // 로그인 아이디(사원 번호)
    private String password; // 로그인 비밀번호

    public static LoginRequestDto of(String loginId, String password) {
        return new LoginRequestDto(loginId, password);
    }

}
