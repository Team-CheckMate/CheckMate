package org.checkmate.common.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.mapper.MemberMapper;

@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberMapper memberMapper = new MemberMapper();

    @Override
    public UserInfo login(ReqLoginIdAndPassword requestDto) {
        log.info(" <<< [ 📢 Call MemberMapper to requestDTO ]");
        Optional<UserInfo> userInfo = memberMapper.findByLoginIdAndPassword(
                requestDto.getLoginId(),
                requestDto.getPassword()
        );

        if (userInfo.isEmpty()) {
            throw new DatabaseException("조회된 회원이 없습니다.");
        }

        log.info(" <<< [ 👷🏻 Register \"UserInfo\" for the login session. ]");
        LoginSession.getInstance(userInfo.get());
        log.info(" >>> [ ✅ Register Success! - UserName is {} ]",  userInfo.get().getEName());

        return userInfo.get();
    }

}
