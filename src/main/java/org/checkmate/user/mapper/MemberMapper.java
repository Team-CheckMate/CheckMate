package org.checkmate.user.mapper;

import static org.checkmate.common.util.FilePath.ORACLE_QUERY_USER;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import lombok.extern.log4j.Log4j2;
import org.checkmate.common.database.DBConnector;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.user.entity.Admin;

@Log4j2
public class MemberMapper {

    private final Properties prop = new Properties();

    private void loadProperties() {
        try {
            log.info(" <<< [ 🤖 Try to Import Query file from XML Path ]");
            InputStream input = new FileInputStream(ORACLE_QUERY_USER.getFilePath());
            prop.loadFromXML(input);
            log.info(" >>> [ ✅ Query file loaded Successfully ]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MemberMapper() {
        loadProperties();
    }

    /**
     * [SELECT] 이용자 정보 조회 기능
     *
     * @param loginId  로그인 아이디
     * @param password 비밀 번호
     * @return UserInfo ResponseDTO 객체
     */
    public Optional<UserInfo> findByLoginIdAndPassword(String loginId, String password) {
        Optional<UserInfo> userInfo = Optional.empty();
        String query = prop.getProperty("findByLoginIdAndPassword");

        log.info(" <<< [ 🤖Executing query to find user by login ID and password. ]");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, loginId);
            preparedStatement.setString(4, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                log.info(" >>> [ ✅ Executed query successfully. ]");
                log.debug(">>> ResultSet: {} ", rs.toString());

                if (rs.next()) {
                    log.info(" <<< [ 🤖 User found, building \"UserInfo\" object... ]");
                    Optional<UserInfo> build = Optional.of(UserInfo.builder()
                            .loginId(rs.getString("login_id"))
                            .teamNo(rs.getLong("team_no"))
                            .deptNo(rs.getLong("dept_no"))
                            .eName(rs.getString("e_name"))
                            .tName(rs.getString("t_name"))
                            .dName(rs.getString("d_name"))
                            .role(rs.getString("role"))
                            .delayCnt(rs.getInt("delay_cnt"))
                            .build());
                    log.info(" >>> [ ✅ The build is complete. ]");
                    return build;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return userInfo;
    }

    /**
     * SQL에 접근하여 관리자의 아이디와 패스워드를 SELECT
     *
     * @param loginId  로그인 아이디
     * @param password 비밀 번호
     * @return Admin 객체
     * @throws SQLException SQL 서버 에러
     */
    public Optional<Admin> findByLoginIdAndPasswordForAdmin(String loginId, String password)
            throws SQLException {
        String query = prop.getProperty("findByLoginIdAndPasswordForAdmin");
        try (Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(Admin.builder()
                            .role(resultSet.getString("role"))
                            .build());
                }
            }
        }
        return Optional.empty();
    }

    /**
     * SQL에 접근하여 이용자의 사원번호와 소속 TEAM을 조회하는 기능
     *
     * @param loginId 로그인 아이디 = 사원번호
     * @return ReadMyInformationResponseDto 객체
     * @throws SQLException SQL 서버 에러
     */
    public Optional<ReadMyInformationResponseDto> getMyPageInfo_findByLoginId(String loginId)
            throws SQLException {
        String query = prop.getProperty("findMyPageInfo");
        try (Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ReadMyInformationResponseDto(
                            resultSet.getString("emp_no"),
                            resultSet.getString("t_name")
                    ));
                }
            }
        }
        return Optional.empty();
    }


    /**
     * 사용자 Password 변경 처리
     *
     * @param loginId 사용자의 loginId
     * @param curPassword 사용자의 현재 비밀번호
     * @param updatePassword 사용자가 변경하고자 하는 새로운 비밀번호
     * @return success: true (정상 처리)
     *         fail(-20001): false (비밀번호 불일치 || 사용자 없음)
     * @throws DatabaseException -20001 이외의 Database Exception
     */
    public Boolean updateMemberPassword(String loginId, String curPassword, String updatePassword) {
        String query = prop.getProperty("updateMemberPasswordByInsert");

        try (Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, curPassword);
            preparedStatement.setString(3, updatePassword);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 20001) {
                return false;
            }
            throw new DatabaseException(e.getMessage());
        }
    }

}
