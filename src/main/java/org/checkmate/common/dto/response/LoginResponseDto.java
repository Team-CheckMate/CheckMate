package org.checkmate.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.checkmate.common.entity.MRole;
import org.checkmate.common.entity.Member;

/**
 * 로그인 응답 객체
 * HISTORY1: 최초 생성                                         [송헌욱  2024.07.24]
 * HISTORY2: DTO필드 추가 및 생성자 추가                          [송헌욱, 이준희  2024.07.24]
 * HISTORY3: 클래스 명 변경, Lombok 적용, 사원 이름 컬럼 추가        [송헌욱  2024.07.25]
 */
@Getter
@ToString
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    private String loginId;   // 로그인ID(사원 번호)
    private String password;  // 비밀번호
    private String eName;      // 사원 이름
    private MRole role;       // 권한 (예: ADMIN - 관리자, BASIC - 일반 사원)
    private int delayCnt;     // 도서 연체 횟수

    public static LoginResponseDto from(Member member) {
        return LoginResponseDto.builder()
                .loginId(member.getLoginId())
                .eName(member.getEName())
                .role(member.getRole())
                .delayCnt(member.getDelayCnt())
                .build();
    }

}
