package org.checkmate.server.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.checkmate.database.DBConnector;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.util.TypeFormatter;

/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.25]
 */
public class BookMapper {

    private final Properties prop = new Properties();

    public BookMapper() {
        try {
            InputStream input = new FileInputStream(
                    "target/classes/org/checkmate/sql/userQuery.xml");
            prop.loadFromXML(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 도서테이블의 책 정보를 모두 조회하는 SELECT 기능
     * @return List<Book> 책 정보를 담은 리스트 컬랙션
     * @throws SQLException SQL 서버 에러
     */
    public List<BookLoanStatus> findAllBookLoanStatus() throws SQLException {
        List<BookLoanStatus> books = new ArrayList<>();
        String query = prop.getProperty("findAllBookLoanStatus");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookLoanStatus book = BookLoanStatus.builder()
                        .bookId(resultSet.getLong("b.book_id"))
                        .ISBN(resultSet.getString("ISBN"))
                        .bName(resultSet.getString("b_name"))
                        .author(resultSet.getString("author"))
                        .publisher(resultSet.getString("publisher"))
                        .lStatus(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status")))
                        .returnPreDate(resultSet.getDate("return_pre_date"))
                        .build();
                books.add(book);
            }
        }

        return books;
    }
}
