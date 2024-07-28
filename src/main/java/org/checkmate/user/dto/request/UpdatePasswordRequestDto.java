package org.checkmate.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 비밀번호 변경 요청 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.24]
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequestDto {

    private long memberId;
    private String nowPw;
    private String changePw;

    public static UpdatePasswordRequestDto of(long memberId, String nowPw, String changePw) {
        return new UpdatePasswordRequestDto(memberId, nowPw, changePw);
    }

}
