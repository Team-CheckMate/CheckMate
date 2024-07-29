package org.checkmate.admin.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminMemberResponseDto {


    private String loginId; // 로그인 아이디
    private String eName; // 사용자 이름
    private String tName; // 팀 이름
    private String dName; // 부서 이름

}

