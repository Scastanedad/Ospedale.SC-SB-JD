package packagee.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private static final String APP_SALT = "Ospedale2025#HospitalSystem";

    private PasswordUtil() {}

    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String salted = APP_SALT + password;
            byte[] bytes = digest.digest(salted.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(64);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible en esta JVM.", e);
        }
    }

    public static boolean verify(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) return false;
        if (isLegacy(storedPassword)) {
            return plainPassword.equals(storedPassword);
        }
        return hash(plainPassword).equals(storedPassword);
    }

    public static boolean isLegacy(String storedPassword) {
        return storedPassword == null || !storedPassword.matches("[0-9a-f]{64}");
    }
}
