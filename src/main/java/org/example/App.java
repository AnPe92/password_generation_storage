package org.example;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){

        Scanner scanner = new Scanner(System.in);
        LoginCredentials loginCredentials = new LoginCredentials();
        boolean running = true;
        while(running){
            System.out.println( loginCredentials.generateNewPassword(20) );
            scanner.nextLine();
        }

    }
}
