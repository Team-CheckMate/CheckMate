package org.checkmate.common.service;

import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;

public interface LoginService {

    UserInfo login(ReqLoginIdAndPassword requestDto);

}
