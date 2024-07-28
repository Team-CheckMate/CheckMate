package org.checkmate.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminMemberResponseDto {
    // TODO: 필요한 클래스인지 확인 필요

    private String login_id; // 로그인 아이디
    private String e_name; // 사용자 이름
    private String t_name; // 팀 이름
    private String d_name; // 부서 이름

    public static AdminMemberResponseDto from(AdminMember AdminMember) {
        return AdminMemberResponseDto.builder()
               .login_id(AdminMember.getLogin_id())
                .e_name(AdminMember.getE_name())
                .t_name(AdminMember.getT_name())
                .d_name(AdminMember.getD_name())
                .build();

    }
}
