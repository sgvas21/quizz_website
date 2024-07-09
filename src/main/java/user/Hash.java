package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    String password; // Stores the password to be hashed

    /*
     * Converts a byte[] array to a hexadecimal String representation.
     * Each byte is represented by 2 characters in the resulting String.
     */
    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff; // Remove higher bits to get unsigned value
            if (val < 16) buff.append('0'); // Ensure two characters per byte
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    // Constructor to initialize with a password
    public Hash(String password) {
        this.password = password;
    }

    // Method to hash the stored password
    public String hashingPassword() {
        return hashingPassword(this.password);
    }

    /*
     * Static method to hash a given password using SHA algorithm.
     * Returns the hashed password as a hexadecimal String.
     */
    public static String hashingPassword(String password) {
        byte[] salt = password.getBytes(); // Convert password to byte array (salt)

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA"); // Create SHA digest instance
            byte[] hashedBytes = digest.digest(salt); // Compute hash of the salted password

            return hexToString(hashedBytes); // Convert hashed bytes to hexadecimal String
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
