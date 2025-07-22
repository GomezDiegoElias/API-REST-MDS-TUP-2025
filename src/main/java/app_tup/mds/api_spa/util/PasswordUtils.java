package app_tup.mds.api_spa.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HexFormat;

public class PasswordUtils {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATIONS = 10000;

    private PasswordUtils() {}

    public static String generateRandomSalt() {
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(SALT_LENGTH, 0, validChars.length())
                .mapToObj(validChars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String hashPasswordWhitSalt(String password, String salt) {
        try {

            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    HASH_ITERATIONS,
                    256
            );

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHash, String salt) {
        String hashedInput = hashPasswordWhitSalt(inputPassword, salt);
        return hashedInput.equals(storedHash);
    }

}
