package org.checkmate.server.dto.response;

import org.checkmate.server.entity.Member;

/**
 * 로그인 응답 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public class LoginResponseDto {

    private final boolean success; // 성공 여부
    private final Member member;   // 서비스 이용자
    private final String message;  // 응답 메세지

    public LoginResponseDto(boolean success, Member member, String message) {
        this.success = success;
        this.member = member;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "success=" + success +
                ", member=" + member +
                ", message='" + message + '\'' +
                '}';
    }

}
