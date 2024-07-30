package org.checkmate.common.controller.server;

import lombok.NoArgsConstructor;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;

@NoArgsConstructor(force = true)
public class LoginController {

    private final LoginService loginService = new LoginServiceImpl();

    public UserInfo getUserInfo(String loginId, String password) {
        ReqLoginIdAndPassword requestDto = ReqLoginIdAndPassword.builder()
                .loginId(loginId)
                .password(password)
                .build();
        return loginService.login(requestDto);
    }

}
