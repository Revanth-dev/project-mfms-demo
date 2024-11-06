package com.payswiff.mfmsproject.dtos;

import java.util.Random;

public class RandomEmailGenerator {

    // Method to generate random email address
    public static String generateRandomEmail() {
        // Define possible characters for the username
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        // Generate a random username with 8 characters (you can adjust this length)
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            username.append(characters.charAt(randomIndex));
        }

        // List of domains to choose from
        String[] domains = {"gmail.com"};
        String domain = domains[random.nextInt(domains.length)];

        // Generate a unique email by combining username with domain
        String email = username.toString() + "@" + domain;

        return email;
    }

   
}
