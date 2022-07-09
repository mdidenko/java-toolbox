package utils;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtils {

    public static class MySQL {

        private final String connectionUrl, login, password;

        public MySQL (final String host, final String port, final String dbName, final String login, final String password)
        {
            this.connectionUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
            this.login = login;
            this.password = password;
        }

        public String[][] executeQuery (final String query){
            try (final Connection connection = DriverManager.getConnection(this.connectionUrl, this.login, this.password)) {
                try (final Statement statement = connection.createStatement();
                     final ResultSet resultSet = statement.executeQuery(query)) {
                    return convertResultSetToStringArray(resultSet);
                } catch (SQLException sqlExc) {
                    throw new RuntimeException(String.format("Failed query (%s) to database.", query), sqlExc);
                }
            } catch (SQLException sqlExc) {
                throw new RuntimeException("Couldn't connect to database.", sqlExc);
            }
        }

        private String[][] convertResultSetToStringArray (final ResultSet resultSet){
            final ArrayList<String[]> arrayList = new ArrayList<>();
            try {
                if (resultSet.isClosed()) {
                    throw new UnsupportedOperationException("Performing actions against closed ResultSet.");
                }
                while (resultSet.next()) {
                    final int numberOfRowColumns = resultSet.getMetaData().getColumnCount();
                    final String[] rowData = new String[numberOfRowColumns];
                    for (int columnIndex = 1; columnIndex <= numberOfRowColumns; columnIndex++) {
                        rowData[columnIndex - 1] = resultSet.getString(columnIndex);
                    }
                    arrayList.add(rowData);
                }
            } catch (SQLException sqlExc) {
                throw new RuntimeException("Failed to convert ResultSet to Array.", sqlExc);
            }
            return arrayList.toArray(new String[arrayList.size()][]);
        }
    }
}
