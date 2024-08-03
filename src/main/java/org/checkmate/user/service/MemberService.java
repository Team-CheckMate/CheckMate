package org.checkmate.user.service;

import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.user.dto.request.ReqLoginIdAndCurPasswordAndUpdatePassword;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

public interface MemberService {

    /**
     * 사용자 Password 변경
     * @param requestDto ReqLoginIdAndCurPasswordAndUpdatePassword: loginId, curPassword, updatePassword
     * @return CommonResponse<T> success: true, message
     */
    CommonResponse<UpdatePasswordResponseDto> updatePassword(
            ReqLoginIdAndCurPasswordAndUpdatePassword requestDto
    );

}
