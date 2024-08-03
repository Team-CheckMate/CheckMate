package org.checkmate.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;
import org.checkmate.user.dto.request.ReqLoginIdAndCurPasswordAndUpdatePassword;
import org.checkmate.user.mapper.MemberMapper;
import org.checkmate.user.service.MemberService;
import org.checkmate.user.service.MemberServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("사용자 테스트")
class MemberServiceTest {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private LoginService loginService = new LoginServiceImpl();
    private MemberService memberService = new MemberServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 로그인_테스트 {

        private UserInfo expectUser;

        @BeforeEach
        void setUp() {
            expectUser = UserInfo.builder()
                    .loginId("1106050003")
                    .teamNo(7L)
                    .deptNo(3L)
                    .eName("정다은")
                    .tName("공개SW기술지원 서비스팀")
                    .dName("ITO사업본부")
                    .role("BASIC")
                    .delayCnt(0)
                    .build();
        }

        @DisplayName("(Green) 데이터베이스에 아이디와 비밀번호가 일치하는 데이터가 있으면 응답 객체를 반환한다.")
        @Test
        void 데이터베이스에_아이디와_비밀번호가_일치하는_데이터가_있으면_응답_객체를_반환한다() throws Exception {
            // given
            String loginId = "1106050003";
            String password = PasswordEncoder.encrypt("0000");

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();

            // when
            UserInfo result = loginService.login(request);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getLoginId()).isEqualTo(expectUser.getLoginId());
            assertThat(result.getEName()).isEqualTo(expectUser.getEName());
            assertThat(result.getRole()).isEqualTo(expectUser.getRole());
        }

        @DisplayName("(Red) 아이디 또는 비밀번호가 다르면 예외가 발생한다.")
        @Test
        void 아이디_또는_비밀번호가_다르면_예외가_발생한다() throws Exception {
            // given
            String wrongLoginId = "1105050003";
            String password = PasswordEncoder.encrypt("0000");

            String loginId = "1106050003";
            String wrongPassword = PasswordEncoder.encrypt("0001");

            ReqLoginIdAndPassword wrongLoginIdInput = ReqLoginIdAndPassword.builder()
                    .loginId(wrongLoginId)
                    .password(password)
                    .build();

            ReqLoginIdAndPassword wrongPasswordInput = ReqLoginIdAndPassword.builder()
                    .loginId(loginId)
                    .password(wrongPassword)
                    .build();

            // when & then
            assertThatThrownBy(() -> loginService.login(wrongLoginIdInput))
                    .isInstanceOf(DatabaseException.class)
                    .hasMessageContaining("조회된 회원이 없습니다.");
            assertThatThrownBy(() -> loginService.login(wrongPasswordInput))
                    .isInstanceOf(DatabaseException.class)
                    .hasMessageContaining("조회된 회원이 없습니다.");
        }

        @DisplayName("(Red) 데이터베이스에서 일치하는 아이디 비밀번호가 없으면 예외가 발생한다.")
        @Test
        void 아이디_비밀번호가_저장된_데이터베이스와_일치하지_않는다면_예외가_발생한다() throws Exception {
            // given
            String loginId = "20240801";
            String password = PasswordEncoder.encrypt("0000");

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();

            // when
            when(memberMapper.findByLoginIdAndPassword(anyString(), anyString()))
                    .thenReturn(Optional.empty());

            // then
            assertThatThrownBy(() -> loginService.login(request))
                    .isInstanceOf(DatabaseException.class)
                    .hasMessageContaining("조회된 회원이 없습니다.");
        }

    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 세션_테스트 {

        private LoginSession loginInfo;
        private UserInfo expectUser;

        @BeforeEach
        void setUp() {
            expectUser = UserInfo.builder()
                    .loginId("1106050003")
                    .teamNo(7L)
                    .deptNo(3L)
                    .eName("정다은")
                    .tName("공개SW기술지원 서비스팀")
                    .dName("ITO사업본부")
                    .role("BASIC")
                    .delayCnt(0)
                    .build();
        }

        @AfterEach
        void clear() {
            if (loginInfo != null) {
                loginInfo.clearSession();
            }
        }

        private LoginSession createLoginSession(String id, String pw) throws Exception {
            String password = PasswordEncoder.encrypt(pw);

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(id)
                    .password(password)
                    .build();

            return LoginSession.getInstance(loginService.login(request));
        }


        @DisplayName("(Green) 사용자는 로그인이 완료되면 세션에 존재하는 유저의 정보를 사용할 수 있다.")
        @Test
        void 로그인이_완료되면_세션에_존재하는_유저의_정보를_사용할_수_있다() throws Exception {
            // given
            String loginId = "1106050003";
            String password = "0000";

            // when
            loginInfo = createLoginSession(loginId, password);
            UserInfo userInfo = loginInfo.getUserInfo();

            // then
            assertThat(loginInfo).isNotNull();
            assertThat(userInfo).isNotNull();
            assertThat(userInfo.getLoginId()).isEqualTo(expectUser.getLoginId());
            assertThat(userInfo.getTeamNo()).isEqualTo(expectUser.getTeamNo());
            assertThat(userInfo.getDeptNo()).isEqualTo(expectUser.getDeptNo());
            assertThat(userInfo.getEName()).isEqualTo(expectUser.getEName());
            assertThat(userInfo.getTName()).isEqualTo(expectUser.getTName());
            assertThat(userInfo.getDName()).isEqualTo(expectUser.getDName());
            assertThat(userInfo.getRole()).isEqualTo(expectUser.getRole());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 비밀번호_변경_테스트 {

        private UserInfo expectUser;

        @BeforeEach
        void setUp() {
            expectUser = UserInfo.builder()
                    .loginId("1106050003")
                    .teamNo(7L)
                    .deptNo(3L)
                    .eName("정다은")
                    .tName("공개SW기술지원 서비스팀")
                    .dName("ITO사업본부")
                    .role("BASIC")
                    .delayCnt(0)
                    .build();
        }

        @AfterEach
        void rollBackPassword() throws Exception {
            String loginId = "1106050003";
            String after = PasswordEncoder.encrypt("0001");
            String exist = PasswordEncoder.encrypt("0000");

            ReqLoginIdAndCurPasswordAndUpdatePassword requestDto = ReqLoginIdAndCurPasswordAndUpdatePassword.builder()
                    .loginId(loginId)
                    .curPassword(after)
                    .updatePassword(exist)
                    .build();

            Boolean updatePassword = memberService.updatePassword(
                    requestDto
            );

            if (!updatePassword) {
                System.out.println("ROLLBACK: No Point!");
            } else {
                System.out.println("ROLLBACK: "+ true);
            }
        }

        @DisplayName("(Green) 현재 비밀번호가 올바르고, 새 비밀번호가 유효하면 비밀번호가 성공적으로 변경된다.")
        @Test
        void 현재_비밀번호가_올바르고_새_비밀번호가_유효하면_비밀번호가_성공적으로_변경된다() throws Exception {
            String loginId = "1106050003";
            String curPwd = PasswordEncoder.encrypt("0000");
            String updatePwd = PasswordEncoder.encrypt("0001");

            ReqLoginIdAndCurPasswordAndUpdatePassword requestDto = ReqLoginIdAndCurPasswordAndUpdatePassword.builder()
                    .loginId(loginId)
                    .curPassword(curPwd)
                    .updatePassword(updatePwd)
                    .build();

            Boolean updatePassword = memberService.updatePassword(
                    requestDto
            );
            System.out.println(updatePassword);
            assertThat(updatePassword).isNotNull();
            assertThat(updatePassword).isTrue();
        }

        @DisplayName("(Red) 현재 비밀번호가 잘못된 경우 비밀번호 변경이 실패한다.")
        @Test
        void 현재_비밀번호가_잘못된_경우_비밀번호_변경이_실패한다() throws Exception {
            String loginId = "1106050003";
            String curPwd = PasswordEncoder.encrypt("00000");
            String updatePwd = PasswordEncoder.encrypt("0001");

            ReqLoginIdAndCurPasswordAndUpdatePassword requestDto = ReqLoginIdAndCurPasswordAndUpdatePassword.builder()
                    .loginId(loginId)
                    .curPassword(curPwd)
                    .updatePassword(updatePwd)
                    .build();

            Boolean updatePassword = memberService.updatePassword(
                    requestDto
            );

            assertThat(updatePassword).isNotNull();
            assertThat(updatePassword).isFalse();
        }
    }

}
