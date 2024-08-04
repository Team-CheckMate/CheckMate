package org.checkmate.common.util;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.dto.response.UserInfo;

/**
 * 로그인 회원의 상태를 저장할 세션 객체
 */
@Getter
@ToString
@Log4j2
public class LoginSession {

    private static LoginSession instance;
    private final UserInfo userInfo;

    public LoginSession(UserInfo userInfo) {
        this.userInfo = userInfo;
        log.info(" >>> [ ✨ LoginSession created]");
    }

    public static synchronized LoginSession getInstance(UserInfo userInfo) {
        if (instance == null) {
            instance = new LoginSession(userInfo);
            return instance;
        }
        return null;
    }

    public static synchronized LoginSession getInstance() {
        if (instance == null) {
            throw new IllegalStateException("세션이 초기화 되지 않았습니다.");
        }
        return instance;
    }

    public void clearSession() {
        instance = null;
    }

}
