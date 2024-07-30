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
import org.checkmate.common.database.DBConnector;
import org.checkmate.admin.dto.response.ApplyStatusResponseDto;


/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [황희정  2024.07.29]

 */
public class ApplyManagementMapper {

    private final Properties prop = new Properties();

    public ApplyManagementMapper() {

        try {
            InputStream input = new FileInputStream("target/classes/org/checkmate/sql/adminQuery.xml");
            prop.loadFromXML(input);
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 신청목록을 모두 조회하는 SELECT 하는 기능
     * @return List<Apply>  사용자 정보를 담은 리스트 컬렉션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<ApplyStatusResponseDto> readApplyStatus() throws SQLException {
        ObservableList<ApplyStatusResponseDto> lists = FXCollections.observableArrayList();
        String query = prop.getProperty("readApplyStatus");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                ApplyStatusResponseDto apply = ApplyStatusResponseDto.builder()
                        .brId(resultSet.getLong("br_id"))
                        .loginId(resultSet.getString("login_id"))
                        .eName(resultSet.getString("e_name"))
                        .bName(resultSet.getString("b_name"))
                        .publisher(resultSet.getString("publisher"))
                        .author(resultSet.getString("author"))
                        .build();
               // System.out.println(apply.toString());
                lists.add(apply);
            }
        }
        return lists;
    }
    /**
     * SQL에 접근하여 신청 승인 -> req_date update
     * @throws SQLException SQL 서버 에러
     */
    public int updateRequestDate(Long brId) throws SQLException {
        String query = prop.getProperty("updateRequestDate");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1,brId);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * SQL에 접근하여 신청 반려 -> req_con_date update
     * @throws SQLException SQL 서버 에러
     */
    public int updateReturnDate(Long brId){
        String query = prop.getProperty("updateReturnDate");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1,brId);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


}
