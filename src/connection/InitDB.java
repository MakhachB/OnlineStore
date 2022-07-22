package connection;

import connection.exceptions.DatabaseDDLException;
import connection.exceptions.DatabaseDMLException;
import properties.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class InitDB {

    public static final String URL_KEY = "db.url";
    public static final String USERNAME_KEY = "db.username";
    public static final String PASSWORD_KEY = "db.password";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private InitDB() {
    }

    public static void init() {
        createTables();
        addDbData();
    }

    private static void createTables() {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);

            statement.addBatch("DROP TABLE IF EXISTS orders");
            statement.addBatch("DROP TABLE IF EXISTS products");
            statement.addBatch("DROP TABLE IF EXISTS clients");
            statement.addBatch("DROP TABLE IF EXISTS employees");

            statement.addBatch("CREATE TABLE IF NOT EXISTS clients( " +
                    "client_id serial PRIMARY KEY," +
                    "last_name varchar(32) NOT NULL, " +
                    "first_name varchar(32) NOT NULL," +
                    "phone varchar(16) UNIQUE ," +
                    "email varchar(64) UNIQUE ," +
                    "country varchar(32) NOT NULL, " +
                    "city varchar(64) NOT NULL," +
                    "CONSTRAINT CHK_phone_email CHECK (phone IS NOT NULL OR email IS NOT NULL)" +
                    ")");

            statement.addBatch("CREATE TABLE IF NOT EXISTS employees( " +
                    "employee_id serial PRIMARY KEY, " +
                    "last_name varchar(32) NOT NULL, " +
                    "first_name varchar(32) NOT NULL, " +
                    "title varchar(128) NOT NULL, " +
                    "phone varchar(16) NOT NULL UNIQUE , " +
                    "email varchar(64) NOT NULL UNIQUE , " +
                    "country varchar(32) NOT NULL, " +
                    "city varchar(64)" +
                    ")");

            statement.addBatch("CREATE TABLE IF NOT EXISTS products(" +
                    "product_id serial PRIMARY KEY, " +
                    "product_name varchar(64) NOT NULL UNIQUE , " +
                    "units_in_stock int NOT NULL," +
                    "unit_price decimal NOT NULL" +
                    ")");

            statement.addBatch("CREATE TABLE IF NOT EXISTS orders(" +
                    "order_id serial PRIMARY KEY, " +
                    "product_id int NOT NULL, " +
                    "client_id int NOT NULL, " +
                    "employee_id int NOT NULL, " +
                    "total_price decimal NOT NULL, " +
                    "quantity int DEFAULT 1, " +
                    "order_date date NOT NULL, " +
                    "CONSTRAINT FK_client_client_id FOREIGN KEY (client_id) REFERENCES clients(client_id), " +
                    "CONSTRAINT FK_employees_employee_id FOREIGN KEY (employee_id) REFERENCES employees(employee_id)," +
                    "CONSTRAINT FK_products_product_id FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                    ")");


            if (statement.executeBatch().length == 8) connection.commit();
            else throw new DatabaseDDLException("Cannot create tables in database");


        } catch (DatabaseDDLException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDbData() {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);
            statement.addBatch("INSERT INTO clients (last_name, first_name, phone, email, country, city) " +
                    "VALUES " +
                    "('Cristiano', 'Ronaldo', '+83492432943', null, 'Portugal', 'Lisbon')," +
                    "('Lionel', 'Messi', null, 'messi@gmail.com','Argentina', 'Buenos Aires')," +
                    "('Abakarov', 'Hizri', '+4342424242', 'hizriii@gmail.com', 'Russia', 'Babayrt')"
            );

            statement.addBatch("INSERT INTO employees (last_name, first_name, title, phone, email, country, city) " +
                    "VALUES " +
                    "('Andrew', 'Johns', 'Sales Manager', '+28582393', 'end@mail.com', 'USA', 'New-york')," +
                    "('Evelin', 'Rodriguez', 'Programmer', '+432424234', 'eve@email.com', 'Canada', 'Ottawa')," +
                    "('Mike', 'Endy', 'Sales Representative', '+432424421', 'mike@aaa.com', 'Russia','Kizlyar')," +
                    "('Michael', 'Jackson', 'Sales Representative', '+8959399593', 'michael@mail.com', 'USA', 'Los-Angeles')");
            statement.addBatch("INSERT INTO products (product_name, units_in_stock, unit_price) " +
                    "VALUES " +
                    "('Samsung TV 2022', 23, 74234.234)," +
                    "('Laptop HP er-15424', 53, 41600.0)," +
                    "('Vodka', 592, 120)," +
                    "('Toyota Land Cruiser 200 2018', 17, 6200000)");

            if (statement.executeBatch().length == 3) connection.commit();
            else throw new DatabaseDMLException("Cannot add test data in database");

        } catch (DatabaseDMLException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
