package org.checkmate.user.entity;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.29]
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Admin {

    private String loginId; // 로그인 ID
    private String password; // 비밀번호
    private String role; // 권한 (예: ADMIN - 관리자)

    @Builder
    public Admin(String loginId, String password, String role) {
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Admin that = (Admin) object;
        return Objects.equals(loginId, that.loginId) && Objects.equals(password,
                that.password) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, password, role);
    }
}
