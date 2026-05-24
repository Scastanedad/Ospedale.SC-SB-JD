package packagee.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad de seguridad para contraseñas.
 *
 * Implementa hashing SHA-256 con sal de aplicación.
 * Incluye soporte de migración transparente: si la contraseña almacenada
 * es texto plano (legacy), se compara directamente y se marca para migración.
 *
 * Principio SOLID: Responsabilidad Única (SRP) — solo gestiona contraseñas.
 * Clase final con constructor privado — no instanciable (utility class).
 */
public final class PasswordUtil {

    /**
     * Sal de aplicación combinada con cada contraseña antes de hashear.
     * Eleva significativamente la seguridad frente a ataques de diccionario
     * y rainbow tables, sin requerir librerías externas.
     */
    private static final String APP_SALT = "Ospedale2025#HospitalSystem";

    // Prevenir instanciación
    private PasswordUtil() {}

    // ── Hash ──────────────────────────────────────────────────────────────────

    /**
     * Calcula el hash SHA-256 de la contraseña con sal de aplicación.
     *
     * @param password contraseña en texto plano
     * @return cadena de 64 caracteres hexadecimales en minúsculas
     */
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
            // SHA-256 está garantizado en toda implementación Java SE (JCA)
            throw new RuntimeException("SHA-256 no disponible en esta JVM.", e);
        }
    }

    // ── Verificación ──────────────────────────────────────────────────────────

    /**
     * Verifica si la contraseña ingresada coincide con la almacenada.
     *
     * Soporta migración transparente:
     * - Si el valor almacenado parece un hash SHA-256 (64 hex), compara hashes.
     * - Si es texto plano (legacy), compara directamente para no romper
     *   cuentas existentes. El llamador debe luego persistir el hash.
     *
     * @param plainPassword contraseña ingresada por el usuario
     * @param storedPassword valor almacenado en el JSON (plain o hash)
     * @return true si la contraseña es correcta
     */
    public static boolean verify(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) return false;
        if (isLegacy(storedPassword)) {
            // Contraseña legacy en texto plano — comparar directamente
            return plainPassword.equals(storedPassword);
        }
        // Contraseña hasheada — comparar hashes
        return hash(plainPassword).equals(storedPassword);
    }

    // ── Detección de legacy ───────────────────────────────────────────────────

    /**
     * Detecta si el valor almacenado es una contraseña legacy (texto plano).
     * Un hash SHA-256 válido es siempre exactamente 64 caracteres hexadecimales.
     *
     * @param storedPassword valor almacenado en el JSON
     * @return true si NO es un hash SHA-256 (es texto plano)
     */
    public static boolean isLegacy(String storedPassword) {
        return storedPassword == null || !storedPassword.matches("[0-9a-f]{64}");
    }
}
