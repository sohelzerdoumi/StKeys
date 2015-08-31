import java.sql.*;

/**
 * .
 */
public class SqliteManager {
    private static Statement statement = null;
    private static Connection connection = null;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            SqliteManager.connection = DriverManager.getConnection("jdbc:sqlite:wireless.db");
            SqliteManager.statement = SqliteManager.connection.createStatement();
            SqliteManager.statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists wirelesses");
            statement.executeUpdate("create table wirelesses (id integer PRIMARY KEY, serial string, hash_serial string)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            SqliteManager.connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void beginTransaction() {
        try {
            SqliteManager.statement.execute("BEGIN");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void endTransaction() {
        try {
            SqliteManager.statement.execute("END TRANSACTION");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertWireless(String serial, String hash) {
        try {
            String query = "insert into wirelesses values(NULL, '%s', '%s')";
            SqliteManager.statement.execute(String.format(query, serial, hash));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
