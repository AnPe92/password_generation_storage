package org.example;

import java.sql.*;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static Scanner scanner = new Scanner(System.in);
    static LoginCredentials loginCredentials = new LoginCredentials();
    public static void main( String[] args ){
        // Create the "my_entity" table
        try (Connection connection = DatabaseConfig.getConnection()) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS login (id IDENTITY PRIMARY KEY, website VARCHAR(255), password VARCHAR(255), username VARCHAR (255))";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean running = true;
        while(running){
            System.out.println("1. Add new password \n2. View stored passwords \n3. Delete a password \n4. Exit");
            String choice = scanner.nextLine();
            switch (choice){
                case "1":
                    addNewPasswordSet();
                    break;
                case "2":
                    loginCredentials.getAllData();
                    break;
                case "3":
                    removePasswordSet();
                    break;
                case "4":
                    running = false;
            }

        }

    }

    private static void removePasswordSet() {
        System.out.println("-----CHOICES-----\n1. Delete passwordset by id\n2. Delete passwordset by website");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                boolean validInput = false;
                while(!validInput) {
                    try {
                        System.out.println("Enter id: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        loginCredentials.deletePasswordById(id);
                        validInput= true;
                    } catch (NumberFormatException e) {
                        System.out.println("---------------\nInput must be a number\n---------------");
                    }
                }
                break;
            case"2":
                System.out.println("Enter website: ");
                String website = scanner.nextLine();
                loginCredentials.deletePasswordByWebsite(website);
                break;
        }
    }

    private static void addNewPasswordSet() {
        String password = "";

        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter where for: ");
        String location = scanner.nextLine();
        System.out.println("Password options: \n1. Add your own password \n2. Generate a random password");
        String choice = scanner.nextLine();

        switch(choice){
            case "1":
                System.out.println("Please enter your password: ");
                password = scanner.nextLine();
                break;
            case "2":
                int length = 0;
                boolean validInput = false;
                while(!validInput) {
                    try {
                        System.out.println("Enter password length between 8 - 100: ");
                        length = Integer.parseInt(scanner.nextLine());
                        if(length > 7 && length < 101){
                        password = loginCredentials.generateNewPassword(length);
                        validInput = true;
                        System.out.println(password);}
                    } catch (NumberFormatException e) {
                        System.out.println("---------------\nInput must be a number\n---------------");
                    }
                }
                break;
            default:
        }
        if(password.length() > 1) {
            loginCredentials.insertLoginCredentialsDb(username, password, location);
        }

        }

}
