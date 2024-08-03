package org.checkmate.user.controller.server;

import lombok.NoArgsConstructor;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.user.dto.request.ReqLoginIdAndCurPasswordAndUpdatePassword;
import org.checkmate.user.service.MemberService;
import org.checkmate.user.service.MemberServiceImpl;

@NoArgsConstructor
public class UserController {

    private final MemberService memberService = new MemberServiceImpl();

    public CommonResponse<Boolean> updatePassword(
            String loginId, String curPassword, String updatePassword
    ) {
        ReqLoginIdAndCurPasswordAndUpdatePassword requestDto = ReqLoginIdAndCurPasswordAndUpdatePassword.builder()
                .loginId(loginId)
                .curPassword(curPassword)
                .updatePassword(updatePassword)
                .build();
        if(!memberService.updatePassword(requestDto)) {
            return CommonResponse.fail("현재 비밀번호가 다르거나 사용자 정보가 존재하지 않습니다.");
        }
        return CommonResponse.success("비밀번호 변경이 완료되었습니다.");
    }
}
