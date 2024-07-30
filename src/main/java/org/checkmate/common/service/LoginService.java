package org.checkmate.common.service;

import java.sql.SQLException;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

/**
 * 회원 서비스 인터페이스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: pw 업데이트 추가                        [이준희  2024.07.25]
 */
public interface LoginService {

    UserInfo login(ReqLoginIdAndPassword requestDto);
    UpdatePasswordResponseDto changePw(UpdatePasswordRequestDto updatePasswordRequestDto) throws SQLException;
    ReadMyInformationResponseDto getMypageInfo(String loginId) throws SQLException;
}
