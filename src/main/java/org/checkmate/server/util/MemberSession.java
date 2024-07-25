package org.checkmate.server.util;

import lombok.Getter;
import org.checkmate.server.dto.response.MemberInfoResponseDto;

/**
 * 로그인 회원의 상태를 저장할 세션 생성
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public class MemberSession {

    @Getter
    private static MemberSession instance;
    @Getter
    private final MemberInfoResponseDto memberInfo;

    public MemberSession(MemberInfoResponseDto memberInfo) {
        this.memberInfo = memberInfo;
        System.out.println("[생성자] Session created: " + memberInfo.toString());
    }

    public static synchronized MemberSession getInstance(MemberInfoResponseDto memberInfo) {
        if (instance == null) {
            instance = new MemberSession(memberInfo);
            return instance;
        }
        return null;
    }

    public void clearSession() {
        instance = null;
    }

}
