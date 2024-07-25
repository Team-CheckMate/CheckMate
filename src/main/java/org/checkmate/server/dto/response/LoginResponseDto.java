package org.checkmate.server.dto.response;

import org.checkmate.server.entity.MRole;
import org.checkmate.server.entity.Member;

/**
 * 로그인 응답 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: DTO필드 추가 및 생성자 추가              [송헌욱, 이준희  2024.07.24]
 */
public class LoginResponseDto {

    private final boolean success; // 성공 여부
    private final Member member;   // 서비스 이용자
    private final MRole mRole;     // 권한(유저,관리자)
    private final String message;  // 응답 메세지

    public LoginResponseDto(boolean success, Member member, MRole mRole,String message) {
        this.success = success;
        this.member = member;
        this.mRole = mRole;
        this.message = message;
    }

    public LoginResponseDto(boolean success, Member member, String message) {
        this.success = success;
        this.member = member;
        this.mRole = MRole.BASIC; // final이므로 기본값 BASIC으로 설정
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public MRole getmRole() {
        return mRole;
    }

    // getMessage 메서드 추가
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
