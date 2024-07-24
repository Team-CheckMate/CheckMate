package org.checkmate.server.entity;

import java.util.Objects;

/**
 * 서비스 이용자 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public class Member {

    private final long memberId;    // 고유 식별자
    private final String loginId;   // 로그인ID(사원 번호)
    private final String password;  // 비밀번호
    private final MRole role;       // 권한 (예: ADMIN - 관리자, BASIC - 일반 사원)
    private final int delayCnt;     // 도서 연체 횟수

    public Member(long memberId, String loginId, String password, MRole role, int delayCnt) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
        this.delayCnt = delayCnt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Member member = (Member) object;
        return memberId == member.memberId && delayCnt == member.delayCnt && Objects.equals(
                loginId, member.loginId) && Objects.equals(password, member.password)
                && role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, loginId, password, role, delayCnt);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", delayCnt=" + delayCnt +
                '}';
    }

}
