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
import org.checkmate.common.database.DBConnector;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.user.entity.Admin;

/**
 * TODO: 주석 달기
 */
public class MemberMapper {

    private final Properties prop = new Properties();

    private void loadProperties() {
        try {
            InputStream input = new FileInputStream(ORACLE_QUERY_USER.getFilePath());
            prop.loadFromXML(input);
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
        System.out.println("query init");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, loginId);
            preparedStatement.setString(4, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("rs.toString() = " + rs.toString());
                if (rs.next()) {
                    return Optional.of(UserInfo.builder()
                            .loginId(rs.getString("login_id"))
                            .teamNo(rs.getLong("team_no"))
                            .deptNo(rs.getLong("dept_no"))
                            .eName(rs.getString("e_name"))
                            .tName(rs.getString("t_name"))
                            .dName(rs.getString("d_name"))
                            .role(rs.getString("role"))
                            .delayCnt(rs.getInt("delay_cnt"))
                            .build()
                    );
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
     * SQL에 접근하여 이용자의 패스워드를 입력받은 값으로 UPDATE 하는 기능
     *
     * @param login_id       로그인 pk
     * @param changePassword 새로운 비밀 번호
     * @return updateRows 변경된 쿼리 개수
     * @throws SQLException SQL 서버 에러
     */
    public int updateMemberPassword(String login_id, String nowPw,String changePassword) throws SQLException {
        String query = prop.getProperty("updateMemberPasswordByInsert");
        try (Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login_id);
            preparedStatement.setString(2, nowPw);
            preparedStatement.setString(3, changePassword);
            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            return -1;
        }
    }

}
