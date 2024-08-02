package org.checkmate.common.service;

import java.sql.SQLException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;
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
            throw new DatabaseException("[Database Exception] 조회된 회원이 없습니다.");
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

    @Override
    public UpdatePasswordResponseDto changePw(UpdatePasswordRequestDto updatePasswordRequestDto) throws SQLException{
        try{
            System.out.println(updatePasswordRequestDto.toString());
           int result = memberMapper.updateMemberPassword(updatePasswordRequestDto.getLoginId(),
                   updatePasswordRequestDto.getNowPw(),updatePasswordRequestDto.getChangePw());
           if(result>0){
               System.out.println("비번 변경 성공");
               return new UpdatePasswordResponseDto(true,"비밀번호 변경이 완료되었습니다.");
           }else if(result==-1){
               return new UpdatePasswordResponseDto(false,"현재 비밀번호가 다릅니다.");
           }else {
               System.out.println("비번 변경 실패");
               return new UpdatePasswordResponseDto(false,"비밀번호 변경에 실패하였습니다.");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
