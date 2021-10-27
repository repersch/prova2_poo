package edu.ifsp.database.utils;

import java.sql.*;

public class ConnectionFactory implements AutoCloseable {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    private ConnectionFactory() {
    }


    public static Connection createConnection() throws SQLException {
        if(connection == null)
            connection = DriverManager.getConnection("jdbc:sqlite:pessoa.db");
        return connection;
    }


    public static PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return createConnection().prepareStatement(sql);
    }


    public static Statement createStatement() throws SQLException {
        return createConnection().createStatement();
    }


    public void closeConnection() throws Exception {
        if(connection != null)
            connection.close();
    }


    @Override
    public void close() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (statement != null) {
            statement.close();
        }
    }
}

