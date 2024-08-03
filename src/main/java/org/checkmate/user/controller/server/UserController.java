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
            String loginId, String curPassword,String updatePassword
    ) {
        ReqLoginIdAndCurPasswordAndUpdatePassword requestDto = ReqLoginIdAndCurPasswordAndUpdatePassword.builder()
                .loginId(loginId)
                .curPassword(curPassword)
                .updatePassword(updatePassword)
                .build();
        return memberService.updatePassword(requestDto);
    }
}
