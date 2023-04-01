package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginCredentials {
    private static final SecureRandom random = new SecureRandom();


    private String username;
    private String password;
    private String website;

    public String generateNewPassword(int length){
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "!\"#$%&()/=?@[\\]_{}";
        String allCharacters = lowerCaseLetters + upperCaseLetters + numbers + specialCharacters;

        List<Character> passwordChars = new ArrayList<>();

        // Add at least one character from each type
        passwordChars.add(randomCharacter(lowerCaseLetters));
        passwordChars.add(randomCharacter(upperCaseLetters));
        passwordChars.add(randomCharacter(numbers));
        passwordChars.add(randomCharacter(specialCharacters));

        // Fill the remaining characters randomly from the combined character set
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(randomCharacter(allCharacters));
        }

        // Shuffle the characters to make the password completely random
        Collections.shuffle(passwordChars);

        // Convert the character list to a string
        StringBuilder password = new StringBuilder();
        for (Character c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    public char randomCharacter(String characters){
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }

    public void insertLoginCredentialsDb(String username, String password, String website) {
        String insertSql = "INSERT INTO login (username, password, website) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, website);
            statement.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user to database.");
            e.printStackTrace();
        }
    }
    public void getAllData(){
        String querySql = "SELECT * FROM login";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querySql)) {

            System.out.println("ID | Username | Password | Website");
            System.out.println("---------------------------");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String website = resultSet.getString("website");
                String id = resultSet.getString("id");

                System.out.printf("%s | %s | %s | %s%n", id, username, password, website);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching data from database.");
            e.printStackTrace();
        }
    }

    public void deletePasswordById(int id) {
        String sqlQuery = "DELETE FROM login WHERE id = ?";
        try(
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            statement.setInt(1,id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Account with ID " + id + " deleted successfully.\n---------------");
            } else {
                System.out.println("Account with ID " + id + " not found.\n---------------");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting user from database.");
            e.printStackTrace();
        }
    }

    public void deletePasswordByWebsite(String website) {
        String sqlQuery = "DELETE FROM login WHERE website = ?";
        try(
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            statement.setString(1,website);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Account for website " + website + " deleted successfully.\n---------------");
            } else {
                System.out.println("Website " + website + " not found.\n---------------");
            }

        } catch (SQLException e) {
            System.out.println("---------------\nError deleting user from database.\n---------------");
            e.printStackTrace();
        }
    }
}
