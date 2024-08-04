package org.checkmate.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import org.junit.jupiter.api.Disabled;
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
    private final LoginService loginService = new LoginServiceImpl();
    private final MemberService memberService = new MemberServiceImpl();

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

            // when & then
            assertThatThrownBy(() -> loginService.login(request))
                    .isInstanceOf(DatabaseException.class)
                    .hasMessageContaining("조회된 회원이 없습니다.");
        }

    }

    @Disabled // TODO: 전체 테스트를 돌리면 이전 테스트에서 남은 session의 정보가 남아있는 현상이 있어 잠시 Disabled 하였습니다.
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 세션_테스트 {

        private LoginSession session;
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
            session.clearSession();
        }

        @DisplayName("(Green) 사용자는 로그인이 완료되면 세션에 존재하는 유저의 정보를 사용할 수 있다.")
        @Test
        void 로그인이_완료되면_세션에_존재하는_유저의_정보를_사용할_수_있다() throws Exception {
            // given
            String loginId = "1106050003";
            String password = PasswordEncoder.encrypt("0000");

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();

            // when
            loginService.login(request);
            session = LoginSession.getInstance();
            System.out.println(session.toString());

            // then
            assertThat(session).isNotNull();
            assertThat(session.getUserInfo().getLoginId()).isEqualTo(expectUser.getLoginId());
            assertThat(session.getUserInfo().getTeamNo()).isEqualTo(expectUser.getTeamNo());
            assertThat(session.getUserInfo().getDeptNo()).isEqualTo(expectUser.getDeptNo());
            assertThat(session.getUserInfo().getEName()).isEqualTo(expectUser.getEName());
            assertThat(session.getUserInfo().getTName()).isEqualTo(expectUser.getTName());
            assertThat(session.getUserInfo().getDName()).isEqualTo(expectUser.getDName());
            assertThat(session.getUserInfo().getRole()).isEqualTo(expectUser.getRole());
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
