package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ValidationUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidPasswordPolicy(String password) {
        if (password == null || password.length() < 6) return false;
        // simple policy: at least one digit and one non-space
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasNonSpace = password.matches(".*\\S.*");
        return hasDigit && hasNonSpace;
    }

    public static String sha256(String base) {
        if (base == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
