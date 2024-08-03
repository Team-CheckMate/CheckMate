package org.checkmate.user.service;

import lombok.RequiredArgsConstructor;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.user.dto.request.ReqLoginIdAndCurPasswordAndUpdatePassword;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;
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
    public CommonResponse<UpdatePasswordResponseDto> updatePassword(
            ReqLoginIdAndCurPasswordAndUpdatePassword requestDto) {

        String loginId = requestDto.getLoginId();
        String curPassword = requestDto.getCurPassword();
        String updatePassword = requestDto.getUpdatePassword();

        boolean result = memberMapper.updateMemberPassword(loginId, curPassword, updatePassword);

        if (!result) {
            return CommonResponse.fail("현재 비밀번호가 다르거나 사용자 정보가 존재하지 않습니다.");
        }
        return CommonResponse.success("비밀번호 변경이 완료되었습니다.");
    }

}
