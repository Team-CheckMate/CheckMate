package org.checkmate.common.service;

import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.common.dto.request.LoginRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;
import org.checkmate.common.dto.response.LoginResponseDto;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.user.entity.Member;
import org.checkmate.user.mapper.MemberMapper;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 회원 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: Optional 타입 선언                     [송헌욱  2024.07.25]
 * HISTORY3: pw update 기능 추가                    [이준희  2024.07.25]
 * HISTORY4: MyPage 정보조회 기능 추가               [이준희  2024.07.26]
 */
public class LoginServiceImpl implements LoginService {

    private final MemberMapper memberMapper;

    public LoginServiceImpl() {
        this.memberMapper = new MemberMapper();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) throws SQLException {
        Optional<Member> member = memberMapper.findByLoginIdAndPassword(
                requestDto.getLoginId(),
                requestDto.getPassword()
        );

        if (member.isEmpty()) {
            throw new SQLException("조회된 회원이 없습니다.");
        }

        Member foundMember = member.get();
        return LoginResponseDto.from(foundMember);
    }

    @Override
    public ReadMyInformationResponseDto getMypageInfo(String loginId) throws SQLException {
            Optional<ReadMyInformationResponseDto> myPageResponsedto = memberMapper.getMyPageInfo_findByLoginId(loginId);
        ReadMyInformationResponseDto myPageInfo = myPageResponsedto.orElseThrow(() -> new NoSuchElementException("조회된 정보가 없습니다."));
        return myPageInfo;
    }

    @Override
    public UpdatePasswordResponseDto changePw(UpdatePasswordRequestDto updatePasswordRequestDto) throws SQLException{
        try{
            System.out.println(updatePasswordRequestDto.toString());
           int result = memberMapper.updateMemberPassword(updatePasswordRequestDto.getLoginId(),
                   updatePasswordRequestDto.getChangePw());
           if(result>0){
               System.out.println("비번 변경 성공");
               return new UpdatePasswordResponseDto(true,"비밀번호 변경이 완료되었습니다.");
           }else{
               System.out.println("비번 변경 실패");
               return new UpdatePasswordResponseDto(false,"비밀번호 변경에 실패하였습니다.");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
