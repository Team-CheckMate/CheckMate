package org.checkmate.server.entity;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서비스 이용자 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: ROLE get메서드 추가                     [이준희  2024.07.24]
 * HISTORY3: Lombok 적용 및 사원 이름 컬럼 추가         [송헌욱  2024.07.25]
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long memberId;    // 고유 식별자
    private String loginId;   // 로그인ID(사원 번호)
    private String password;  // 비밀번호
    private String eName;      // 사원 이름
    private MRole role;       // 권한 (예: ADMIN - 관리자, BASIC - 일반 사원)
    private int delayCnt;     // 도서 연체 횟수

    @Builder
    public Member(Long memberId, String loginId, String password, String eName, MRole role, int delayCnt) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.eName = eName;
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
        return delayCnt == member.delayCnt && Objects.equals(memberId, member.memberId)
                && Objects.equals(loginId, member.loginId) && Objects.equals(
                password, member.password) && Objects.equals(eName, member.eName)
                && role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, loginId, password, eName, role, delayCnt);
    }
}
