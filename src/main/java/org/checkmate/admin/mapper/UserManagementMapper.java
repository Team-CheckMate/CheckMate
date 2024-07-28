package org.checkmate.admin.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.checkmate.database.DBConnector;
import org.checkmate.server.entity.AdminMember;


/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [황희정  2024.07.27]

 */
public class UserManagementMapper {

    private final Properties prop = new Properties();

    public UserManagementMapper() {

        try {
            InputStream input = new FileInputStream("target/classes/org/checkmate/sql/adminQuery.xml");
            prop.loadFromXML(input);
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 사용자를 모두 조회하는 SELECT 하는 기능
     * @return List<Member>  사용자 정보를 담은 리스트 컬렉션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<AdminMember> findByMember() throws SQLException {
        ObservableList<AdminMember> members = FXCollections.observableArrayList();
        String query = prop.getProperty("findByMember");
        CheckBox ch ;

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ch = new CheckBox();
                AdminMember member = AdminMember.builder()
                        .select(ch)
                        .login_id(resultSet.getString("login_id"))
                        .e_name(resultSet.getString("e_name"))
                        .t_name(resultSet.getString("t_name"))
                        .d_name(resultSet.getString("d_name"))
                        .build();
                members.add(member);
            }
        }

        return members;
    }

    public int userDelete(String loginId) throws SQLException {
        String query = prop.getProperty("userDelete");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
                preparedStatement.setString(1,loginId);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


}
