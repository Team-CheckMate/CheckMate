package org.checkmate.server.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import org.checkmate.database.DBConnector;
import org.checkmate.server.entity.MRole;
import org.checkmate.server.entity.Member;

/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: Optional 타입 선언                     [송헌욱  2024.07.25]
 */
public class MemberMapper {

    private final Properties prop = new Properties();

    public MemberMapper() {
        try {
            InputStream input = new FileInputStream("target/classes/org/checkmate/sql/userQuery.xml");
            prop.loadFromXML(input);
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

}
