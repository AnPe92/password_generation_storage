package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.SecureRandom;
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
}
