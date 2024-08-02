package org.checkmate.user.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.checkmate.common.database.DBConnector;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.response.ReadBookRequestResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.common.util.TypeFormatter;
import org.checkmate.user.dto.response.TeamMemberLoanStatusDegree;

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
     *
     * @return List<Book> 책 정보를 담은 리스트 컬랙션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<ReadLoanStatusResponseDto> findAllBookLoanStatus() throws SQLException {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllBookLoanStatus");
        CheckBox ch = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ch = new CheckBox();
                ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                        .bookId(resultSet.getLong("book_id"))
                        .ISBN(resultSet.getString("ISBN"))
                        .bName(resultSet.getString("b_name"))
                        .author(resultSet.getString("author"))
                        .publisher(resultSet.getString("publisher"))
                        .lStatus(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status")))
                        .returnPreDate(resultSet.getDate("return_pre_date"))
                        .select(ch)
                        .build();
                books.add(book);
            }
        }

        return books;
    }

    public void createLoanBook(CreateBookLoanRequestDto requestDto) {
        String query = prop.getProperty("addBookLoanRecord");
        try (Connection connection = DBConnector.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);
        ) {
            for (ReadLoanStatusResponseDto bean : requestDto.getBookList()) {
                callableStatement.setLong(1, bean.getBookId());
                callableStatement.setString(2, requestDto.getLoginId());
                callableStatement.setString(3, bean.getBName());
                callableStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<ReadLoanStatusResponseDto> findByBookName(String searchName)
            throws SQLException {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findByBookName");
        CheckBox ch = null;

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, "%" + searchName + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ch = new CheckBox();
                    ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                            .bookId(resultSet.getLong("book_id"))
                            .ISBN(resultSet.getString("ISBN"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .lStatus(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status")))
                            .returnPreDate(resultSet.getDate("return_pre_date"))
                            .select(ch)
                            .build();
                    books.add(book);
                }
            }

        }

        return books;
    }
    public ObservableList<ReadLoanStatusResponseDto> findLoanBookNotReturnByLoginId(String loginId) {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findLoanBookNotReturnByLoginId");
        CheckBox ch = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, loginId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ch = new CheckBox();
                    ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                            .bookId(resultSet.getLong("book_id"))
                            .blrId(resultSet.getLong("blr_id"))
                            .ISBN(resultSet.getString("ISBN"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .loanDate(resultSet.getDate("loan_date"))
                            .returnDate(resultSet.getDate("return_date"))
                            .returnPreDate(resultSet.getDate("return_pre_date"))
                            .select(ch)
                            .build();
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public void updateReturnLoanBook(ObservableList<ReadLoanStatusResponseDto> selectedBooks) {
        String query = prop.getProperty("updateReturnLoanBook");
        try(Connection connection = DBConnector.getInstance().getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
        ) {
            for(ReadLoanStatusResponseDto bean : selectedBooks) {
                callableStatement.setLong(1, bean.getBookId());
                callableStatement.setLong(2, bean.getBlrId());
                callableStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<ReadLoanStatusResponseDto> findAllReadMyBooks(String loginId) {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllReadMyBooks");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, loginId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                            .bookId(resultSet.getLong("book_id"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .loanDate(resultSet.getDate("loan_date"))
                            .returnDate(resultSet.getDate("return_date"))
                            .build();
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public int getOverdueBookCount(String loginId) {
        String query = prop.getProperty("getOverdueBookCount");
        int result = 0;
        try(Connection connection = DBConnector.getInstance().getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
        ) {
            callableStatement.setString(1, loginId);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            result = callableStatement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public ObservableList<ReadLoanStatusResponseDto> findOverdueLoanBook(String loginId) {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findOverdueLoanBook");
        CheckBox ch = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, loginId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ch = new CheckBox();
                    ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                            .bookId(resultSet.getLong("book_id"))
                            .blrId(resultSet.getLong("blr_id"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .loanDate(resultSet.getDate("loan_date"))
                            .returnDate(resultSet.getDate("return_date"))
                            .returnPreDate(resultSet.getDate("return_pre_date"))
                            .select(ch)
                            .build();
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public void updateReturnOverdueLoanBook(String loginId, ObservableList<ReadLoanStatusResponseDto> selectedBooks) {
        String query = prop.getProperty("updateReturnOverdueLoanBook");
        try(Connection connection = DBConnector.getInstance().getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
        ) {
            for(ReadLoanStatusResponseDto bean : selectedBooks) {
                callableStatement.setLong(1, bean.getBookId());
                callableStatement.setLong(2, bean.getBlrId());
                callableStatement.setString(3, loginId);
                callableStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 나의 부서 대여 현황을 위해 필요한 데이터 가져오기 위한 DataBase Connection 로직
     *
     * @param teamNo 부서 번호
     * @return List<TeamMemberLoanStatusDegree> 필요한 요청에 대한 응답 객체를 담은 List 타입 객체
     * @throws DatabaseException DataBase 예외
     */
    public List<TeamMemberLoanStatusDegree> findTeamMemberLoanStatus(Long teamNo) {
        List<TeamMemberLoanStatusDegree> list = new ArrayList<>();
        String query = prop.getProperty("findTeamMemberLoanStatus");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, teamNo);
            System.out.println(" >>> ? ");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                TeamMemberLoanStatusDegree dto = TeamMemberLoanStatusDegree.builder()
                        .loginId(rs.getString("login_id"))
                        .eName(rs.getString("e_name"))
                        .bookCount(rs.getInt("book_count"))
                        .currentMonthCount(rs.getInt("current_month_count"))
                        .lastMonthCount(rs.getInt("last_month_count"))
                        .lastYearCount(rs.getInt("last_year_count"))
                        .build();
                list.add(dto);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return list;
    }

    public boolean createBookRequest(String loginId, String bName, String publisher, String author) {
        String query = prop.getProperty("createBookRequest");
        boolean isSuccess = false;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1,loginId);
            preparedStatement.setString(2,bName);
            preparedStatement.setString(3,author);
            preparedStatement.setString(4,publisher);
            int result = preparedStatement.executeUpdate();
            isSuccess = true;
        }
        catch (SQLException e){
            //throw new RuntimeException(e);
        }
        return isSuccess;
    }

    public ObservableList<ReadBookRequestResponseDto> findAllBookRequest(long deptNo) {
        ObservableList<ReadBookRequestResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllBookRequest");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, deptNo);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReadBookRequestResponseDto book = ReadBookRequestResponseDto.builder()
                            .rownum(resultSet.getLong("rownum"))
                            .loginId(resultSet.getString("login_id"))
                            .eName(resultSet.getString("e_name"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .status(resultSet.getString("status"))
                            .build();
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }
}
