package org.checkmate.admin.dto.response;
import java.util.Objects;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [Users] 도서 대여 정보 응답 객체
 * HISTORY1: 최초 생성                                         [송헌욱  2024.07.25]
 * HISTORY2: CheckBox Field 생성                              [권혁규  2024.07.26]
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMember {
    // TODO: 필요한 클래스인지 확인 필요
    private CheckBox select; //checkbox
    private String login_id; // 로그인 아이디
    private String e_name; // 사용자 이름
    private String t_name; // 팀 이름
    private String d_name; // 부서 이름
    private Button delete;

    @Builder
    public AdminMember(CheckBox select,String login_id, String e_name, String t_name,String d_name ,Button delete) {
        this.select = select;
        this.login_id = login_id;
        this.e_name = e_name;
        this.t_name = t_name;
        this.d_name = d_name;
        this.delete = delete;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AdminMember AdminMember = (AdminMember) object;
        return Objects.equals(login_id, AdminMember.login_id) && Objects.equals(e_name,
                AdminMember.e_name) && Objects.equals(t_name, AdminMember.t_name)
                && Objects.equals(d_name, AdminMember.d_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login_id,e_name, t_name,d_name);
    }

}
