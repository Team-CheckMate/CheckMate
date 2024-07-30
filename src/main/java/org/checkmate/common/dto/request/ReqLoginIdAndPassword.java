package org.checkmate.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TODO: 주석 달기
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReqLoginIdAndPassword {

    private String loginId;  // 로그인 아이디(사원 번호)
    private String password; // 로그인 비밀번호

}
