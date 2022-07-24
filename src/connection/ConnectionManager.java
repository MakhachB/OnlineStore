package connection;

import connection.exceptions.DatabaseDMLException;
import connection.properties.PropertiesUtil;
import org.postgresql.util.PSQLException;
import person.Client;
import person.Employee;
import product.Product;
import view.MainView;
import view.clientView.ClientActive;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                        resultSet.getInt("client_id"),
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
                        resultSet.getInt("employee_id"),
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
            int res = preparedStatement.executeUpdate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT client_id FROM clients " +
                    "WHERE phone = '" + phone +
                    "' LIMIT 1");
            int id = -1;
            if (resultSet.next()) id = resultSet.getInt(1);
            if (id != -1) {
                ClientActive.activeClient = new Client(id,
                        last_name,
                        first_name,
                        phone,
                        email,
                        country,
                        city,
                        password);
                System.out.println(res);
                System.out.println(res);
                return res;
            } else {
                throw new DatabaseDMLException("Cannot execute query!");
            }


        } catch (DatabaseDMLException e) {
            System.out.println("Error in database, cannot add client to DB!");
            return -3;
        } catch (PSQLException e) {
//            e.printStackTrace();
            return -2;
        } catch (SQLException e) {
            System.out.println("Error in database!");
            return -4;
        }
    }


    public static List<Product> getProducts() {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * " +
                    "FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String product_name = resultSet.getString("product_name");
                int units_in_stock = resultSet.getInt("units_in_stock");
                double unit_price = resultSet.getDouble("unit_price");

                products.add(new Product(id, product_name, units_in_stock, unit_price));

            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean buyProduct(Product active, int count) {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {
            int newCount = active.getUnits_in_stock() - count;
            if (newCount < 0) throw new DatabaseDMLException("Cannot execute query, more items ordered than available");

            connection.setAutoCommit(false);
            updateProduct(active, newCount, statement);
            addOrder(active, count, statement);
            if (statement.executeBatch().length == 2) {
                connection.commit();
                return true;
            } else {
                throw new DatabaseDMLException("Cannot execute query. Unexpected error.");
            }
        } catch (DatabaseDMLException e) {
//            throw new RuntimeException(e);
            System.out.println("Illegal count of products!");
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateProduct(Product active, int count, Statement statement) throws SQLException {
        statement.addBatch("UPDATE products " +
                "SET units_in_stock = " +  count + " " +
                "WHERE product_id =" + active.getId() + "");
    }

    private static void addOrder(Product active, int count, Statement statement) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        statement.addBatch("INSERT INTO orders (product_id, client_id, employee_id, total_price, quantity, order_date) VALUES " +
                "(" + active.getId() +
                ", " + ClientActive.activeClient.getId() +
                ", 1" +
                ", " + count * active.getUnit_price() +
                ", " + count +
                ", '" + format.format(new Date()) + "' )");
    }
}
