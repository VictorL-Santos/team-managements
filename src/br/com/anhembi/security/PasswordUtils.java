package br.com.anhembi.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean checkPassword(String candidatePassword, String hashedPassword) {
            return encoder.matches(candidatePassword, hashedPassword);
    }
}
