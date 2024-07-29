package org.checkmate.common.service;

import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import java.sql.SQLException;
import org.checkmate.common.dto.request.LoginRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;
import org.checkmate.common.dto.response.LoginResponseDto;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;

/**
 * 회원 서비스 인터페이스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: pw 업데이트 추가                        [이준희  2024.07.25]
 */
public interface LoginService {

    LoginResponseDto login(LoginRequestDto requestDto) throws SQLException;
    UpdatePasswordResponseDto changePw(UpdatePasswordRequestDto updatePasswordRequestDto) throws SQLException;
    ReadMyInformationResponseDto getMypageInfo(String loginId) throws SQLException;
}
