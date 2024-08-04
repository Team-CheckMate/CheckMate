package org.checkmate.common.service;

import org.checkmate.common.dto.request.ReqLoginIdAndPassword;

public interface LoginService {

    void login(ReqLoginIdAndPassword requestDto);

}
