package org.checkmate.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database 연결 관리 목적의 싱글톤 클래스
 * HISTORY1: 최초 설정                              [송헌욱  2024.07.23]
 * HISTORY2: autoCommit false 설정                  [이준희  2024.07.23]
 */
public class DBConnector {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    private static final String USERNAME = "checkmate";
    private static final String PASSWORD = "checkmate";

    private static volatile DBConnector instance;

    public static DBConnector getInstance() {
        if (instance == null) {
            synchronized (DBConnector.class) {
                if (instance == null) {
                    instance = new DBConnector();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

}
