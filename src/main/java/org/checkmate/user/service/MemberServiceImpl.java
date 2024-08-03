package org.checkmate.user.service;

import lombok.RequiredArgsConstructor;
import org.checkmate.user.dto.request.ReqLoginIdAndCurPasswordAndUpdatePassword;
import org.checkmate.user.mapper.MemberMapper;

@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper = new MemberMapper();

    /**
     * 사용자 Password 변경
     * @param requestDto ReqLoginIdAndCurPasswordAndUpdatePassword
     *                   - loginId: 사용자의 loginId
     *                   - curPassword: 현재 비밀번호
     *                   - updatePassword: 변경할 비밀번호
     * @return CommonResponse<T>
     *                   - success: true, message
     *                   - fail: false, message
     */
    @Override
    public Boolean updatePassword(ReqLoginIdAndCurPasswordAndUpdatePassword requestDto) {
        return memberMapper.updateMemberPassword(
                requestDto.getLoginId(),
                requestDto.getCurPassword(),
                requestDto.getUpdatePassword()
        );
    }

}
