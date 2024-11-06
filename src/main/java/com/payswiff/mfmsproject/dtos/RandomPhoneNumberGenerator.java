package com.payswiff.mfmsproject.dtos;

import java.util.Random;

public class RandomPhoneNumberGenerator {

    // Method to generate a random phone number
    public static String generateRandomPhoneNumber() {
        Random random = new Random();

        // Randomly generate a 10-digit phone number (e.g., 123-456-7890)
        StringBuilder phoneNumber = new StringBuilder();
        
        // First part (area code): 3 digits
        phoneNumber.append("(").append(random.nextInt(900) + 100).append(") "); // ensures it's a 3-digit number starting from 100 to 999
        
        // Second part: 3 digits
        phoneNumber.append(random.nextInt(900) + 100).append("-"); // ensures it's a 3-digit number starting from 100 to 999
        
        // Third part: 4 digits
        phoneNumber.append(random.nextInt(9000) + 1000); // ensures it's a 4-digit number starting from 1000 to 9999

        return phoneNumber.toString();
    }

  
}
