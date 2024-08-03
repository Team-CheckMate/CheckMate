package org.checkmate.common.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.user.mapper.MemberMapper;

@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberMapper memberMapper = new MemberMapper();

    @Override
    public UserInfo login(ReqLoginIdAndPassword requestDto) {
        Optional<UserInfo> userInfo = memberMapper.findByLoginIdAndPassword(
                requestDto.getLoginId(),
                requestDto.getPassword()
        );

        if (userInfo.isEmpty()) {
            throw new DatabaseException("조회된 회원이 없습니다.");
        }

        UserInfo user = userInfo.get();

        return UserInfo.builder()
                .loginId(user.getLoginId())
                .teamNo(user.getTeamNo())
                .deptNo(user.getDeptNo())
                .eName(user.getEName())
                .tName(user.getTName())
                .dName(user.getDName())
                .role(user.getRole())
                .delayCnt(user.getDelayCnt())
                .build();
    }

}
