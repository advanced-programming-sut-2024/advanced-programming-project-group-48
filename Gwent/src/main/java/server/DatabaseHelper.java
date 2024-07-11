package server;

import model.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static final String dbURL = "jdbc:sqlite:database/gwent.db";



    public static void initializeDatabase() {
        // Check and create the directory if it does not exist
        File directory = new File("database");
        if (!directory.exists()) {
            directory.mkdirs(); // This will create the directory along with any necessary parent directories.
        }

        // SQL statement for creating a new table
        String sqlCreateUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL,
            nickname TEXT NOT NULL,
            password TEXT NOT NULL,
            email TEXT NOT NULL,
            answer1 TEXT,
            answer2 TEXT,
            answer3 TEXT
            );""";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            // Create a new table
            stmt.execute(sqlCreateUsersTable);
            System.out.println("Table 'users' created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, nickname, password, email, answer1, answer2, answer3) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getAnswerOfQuestions().get(0));
            pstmt.setString(6, user.getAnswerOfQuestions().get(1));
            pstmt.setString(7, user.getAnswerOfQuestions().get(2));
            pstmt.executeUpdate();
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        // Update the SQL query to include answer fields
        String sql = "SELECT username, nickname, password, email, answer1, answer2, answer3 FROM users";

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String nickname = rs.getString("nickname");
                String password = rs.getString("password");
                String email = rs.getString("email");
                // Retrieve answer fields
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                // Assuming updated constructor User(String username, String nickname, String password, String email, String answer1, String answer2, String answer3)
                User user = new User(username, nickname, password, email, answer1, answer2, answer3);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static boolean userExists(String username, String email) {
        String sql = "SELECT id FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Return true if a row is found
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Assuming User constructor User(String username, String nickname, String password, String email, String answer1, String answer2, String answer3)
                return new User(rs.getString("username"), rs.getString("nickname"), rs.getString("password"), rs.getString("email"), rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if no user is found
    }
}