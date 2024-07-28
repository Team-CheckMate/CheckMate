package org.checkmate.user.mapper;

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
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.common.entity.MRole;
import org.checkmate.common.entity.Member;

/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: Optional 타입 선언                     [송헌욱  2024.07.25]
 * HISTORY3: 패스워드 Update 매핑 추가                [이준희  2024.07.25]
 * HISTORY4: MyPage 조회 정보 매핑 추가,admin prop 추가 [이준희  2024.07.25]
 */
public class MemberMapper {

    private final Properties prop = new Properties();
    private final Properties admin_prop = new Properties();

    public MemberMapper() {
        try {
            InputStream input = new FileInputStream("target/classes/org/checkmate/sql/userQuery.xml");
            prop.loadFromXML(input);

            InputStream adminInput = new FileInputStream(
                    "target/classes/org/checkmate/sql/adminQuery.xml");
            admin_prop.loadFromXML(adminInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 이용자의 아이디와 패스워드를 SELECT 하는 기능
     * @param loginId 로그인 아이디
     * @param password 비밀 번호
     * @return Member 객체
     * @throws SQLException SQL 서버 에러
     */
    public Optional<Member> findByLoginIdAndPassword (String loginId, String password) throws SQLException {
        String query = prop.getProperty("findByLoginIdAndPassword");
        try (Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Member(
                            resultSet.getLong("member_id"),
                            resultSet.getString("login_id"),
                            resultSet.getString("password"),
                            resultSet.getString("e_name"),
                            MRole.valueOf(resultSet.getString("role")),
                            resultSet.getInt("delay_cnt")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * SQL에 접근하여 이용자의 사원번호와 소속 TEAM을 조회하는 기능
     * @param loginId 로그인 아이디 = 사원번호
     * @return ReadMyInformationResponseDto 객체
     * @throws SQLException SQL 서버 에러
     */
    public Optional<ReadMyInformationResponseDto> getMyPageInfo_findByLoginId (String loginId) throws SQLException {
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
     * @param memberId 로그인 pk
     * @param changePassword 새로운 비밀 번호
     * @return updateRows 변경된 쿼리 개수
     * @throws SQLException SQL 서버 에러
     */
    public int updateMemberPassword  (long memberId, String changePassword) throws SQLException {
        String query = prop.getProperty("updateMemberPassword");
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, memberId);
            preparedStatement.setString(2, changePassword);
            return preparedStatement.executeUpdate();
        }
    }

}
