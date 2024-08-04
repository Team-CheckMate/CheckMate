package org.checkmate.common.controller.server;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;

@Log4j2
@NoArgsConstructor
public class LoginController {

    private final LoginService loginService = new LoginServiceImpl();

    public CommonResponse<Void> login(String loginId, String password) {
        log.info(" >>> [ ✨ Creates a Request DTO ]");
        ReqLoginIdAndPassword requestDto = ReqLoginIdAndPassword.builder()
                .loginId(loginId)
                .password(password)
                .build();
        log.info(" <<< [ 📢 Call LoginService to requestDTO ]");
        loginService.login(requestDto);
        log.info(" >>> [ ✅ Successful Login Request DTO ]");
        return CommonResponse.success("로그인 성공");
    }

}
