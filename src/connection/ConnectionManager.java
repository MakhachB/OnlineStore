package connection;

import connection.properties.PropertiesUtil;
import org.postgresql.util.PSQLException;
import person.Client;
import person.Employee;
import view.MainView;

import java.sql.*;

public final class ConnectionManager {


    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";


    public static boolean isSignedClient(String phone, String password) {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE " +
                    " phone = '" + phone + "' AND password = '" + password + "' LIMIT 1");

            if (resultSet.next()) {
                MainView.activePerson = new Client(
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("password"));
                return true;
            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSignedEmployee(String phone, String password) {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees WHERE " +
                    " phone = '" + phone + "' AND password = '" + password + "' LIMIT 1");

            if (resultSet.next()) {
                MainView.activePerson = new Employee(
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("password"),
                        resultSet.getString("title"));

                return true;
            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int regClient(String last_name, String first_name, String phone, String email, String country, String city, String password) {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY))) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO clients (last_name, first_name, phone, email, country, city, password) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)");
            preparedStatement.setString(1, last_name);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, country);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, password);
            return preparedStatement.executeUpdate();


        } catch (PSQLException e) {
            return -2;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
