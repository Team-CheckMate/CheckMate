package org.checkmate.common.util;

import lombok.Getter;
import org.checkmate.common.dto.response.LoginResponseDto;

/**
 * 로그인 회원의 상태를 저장할 세션 생성
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public class LoginSession {

    @Getter
    private static LoginSession instance;
    @Getter
    private final LoginResponseDto memberInfo;

    public LoginSession(LoginResponseDto memberInfo) {
        this.memberInfo = memberInfo;
        System.out.println("[생성자] Session created: " + memberInfo.toString());
    }

    public static synchronized LoginSession getInstance(LoginResponseDto memberInfo) {
        if (instance == null) {
            instance = new LoginSession(memberInfo);
            return instance;
        }
        return null;
    }

    public void clearSession() {
        instance = null;
    }

}
