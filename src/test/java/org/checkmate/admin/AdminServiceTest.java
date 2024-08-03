package org.checkmate.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.checkmate.common.dto.request.ReqLoginIdAndPassword;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;
import org.checkmate.user.mapper.MemberMapper;
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

@DisplayName("관리자 테스트")
class AdminServiceTest {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private LoginService loginService = new LoginServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 로그인_테스트 {

        private UserInfo expectAdmin;

        @BeforeEach
        void setUp() {
            expectAdmin = UserInfo.builder()
                    .role("ADMIN")
                    .build();
        }

        @DisplayName("(Green) 데이터베이스에 아이디와 비밀번호가 일치하는 데이터가 있으면 응답 객체를 반환한다.")
        @Test
        void 데이터베이스에_아이디와_비밀번호가_일치하는_데이터가_있으면_응답_객체를_반환한다() throws Exception {
            // given
            String loginId = "1";
            String password = PasswordEncoder.encrypt("0000");

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();

            // when
            UserInfo result = loginService.login(request);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getRole()).isEqualTo(expectAdmin.getRole());
        }

        @DisplayName("(Red) 아이디 또는 비밀번호가 다르면 예외가 발생한다.")
        @Test
        void 아이디_또는_비밀번호가_다르면_예외가_발생한다() throws NoSuchAlgorithmException {
            // given
            String wrongLoginId = "123";
            String password = PasswordEncoder.encrypt("0000");

            String loginId = "1";
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
        void 아이디_비밀번호가_저장된_데이터베이스와_일치하지_않는다면_예외가_발생한다() throws NoSuchAlgorithmException {
            // given
            String loginId = "123";
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
        private UserInfo expectAdmin;

        @BeforeEach
        void setUp() {
            expectAdmin = UserInfo.builder()
                    .loginId("1")
                    .teamNo(0L)
                    .deptNo(0L)
                    .role("ADMIN")
                    .build();
        }

        @AfterEach
        void clear() {
            if (loginInfo != null) {
                loginInfo.clearSession();
            }
        }

        private LoginSession createLoginSession(String id, String pw)
                throws NoSuchAlgorithmException {
            String password = PasswordEncoder.encrypt(pw);

            ReqLoginIdAndPassword request = ReqLoginIdAndPassword.builder()
                    .loginId(id)
                    .password(password)
                    .build();

            return LoginSession.getInstance(loginService.login(request));
        }

        @DisplayName("(Green) 관리자는 로그인이 완료되면 세션에 존재하는 관리자의 정보를 사용할 수 있다.")
        @Test
        void 관리자는_로그인이_완료되면_세션에_존재하는_관리자의_정보를_사용할_수_있다()
                throws NoSuchAlgorithmException {
            // given
            String loginId = "1";
            String password = "0000";

            // when
            loginInfo = createLoginSession(loginId, password);
            UserInfo adminInfo = loginInfo.getUserInfo();

            // then
            assertThat(loginInfo).isNotNull();
            assertThat(adminInfo).isNotNull();
            assertThat(adminInfo.getLoginId()).isEqualTo(expectAdmin.getLoginId());
            assertThat(adminInfo.getTeamNo()).isEqualTo(expectAdmin.getTeamNo());
            assertThat(adminInfo.getDeptNo()).isEqualTo(expectAdmin.getDeptNo());
            assertThat(adminInfo.getEName()).isEqualTo(expectAdmin.getEName());
            assertThat(adminInfo.getTName()).isEqualTo(expectAdmin.getTName());
            assertThat(adminInfo.getDName()).isEqualTo(expectAdmin.getDName());
            assertThat(adminInfo.getRole()).isEqualTo(expectAdmin.getRole());
        }
    }

}
