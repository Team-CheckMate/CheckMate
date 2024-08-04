package org.checkmate.common.controller.server;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;

@Log4j2
@NoArgsConstructor
public class LoginController {

    private final LoginService loginService = new LoginServiceImpl();

    public UserInfo getUserInfo(String loginId, String password) {
        log.info(" >>> [ ✨ Creates a Request DTO ]");
        ReqLoginIdAndPassword requestDto = ReqLoginIdAndPassword.builder()
                .loginId(loginId)
                .password(password)
                .build();
        log.info(" <<< [ 📢 Call LoginService to requestDTO ]");
        return loginService.login(requestDto);
    }

}
