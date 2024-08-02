package org.checkmate.common.service;

import java.sql.SQLException;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

public interface LoginService {

    UserInfo login(ReqLoginIdAndPassword requestDto);
    UpdatePasswordResponseDto changePw(UpdatePasswordRequestDto updatePasswordRequestDto) throws SQLException;

}
