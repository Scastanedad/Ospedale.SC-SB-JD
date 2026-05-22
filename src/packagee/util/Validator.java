package packagee.util;

import java.util.regex.Pattern;

/**
 * Clase utilitaria con validaciones de negocio.
 * Solo métodos estáticos. Sin estado.
 * Las vistas NO validan — todo pasa por aquí o por los servicios.
 */
public final class Validator {

    // Prevenir instanciación
    private Validator() {}

    // ── Patrones precompilados ─────────────────────────────────────────────────

    /** Formato email básico */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /** L-XXXXXXXXXX MTL (L- seguido de 10 dígitos, espacio, MTL) */
    private static final Pattern LICENSE_PATTERN =
            Pattern.compile("^L-\\d{10} MTL$");

    /** O-XXX (O- seguido de exactamente 3 caracteres alfanuméricos) */
    private static final Pattern OFFICE_PATTERN =
            Pattern.compile("^O-[A-Za-z0-9]{3}$");

    /** AAAA-MM-DD */
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    /** hh:mm */
    private static final Pattern TIME_PATTERN =
            Pattern.compile("^([01]\\d|2[0-3]):(00|15|30|45)$");

    /** Solo dígitos, de longitud variable */
    private static final Pattern DIGITS_ONLY = Pattern.compile("^\\d+$");

    // ── Validaciones ──────────────────────────────────────────────────────────

    /**
     * Verifica que el ID tenga exactamente 12 dígitos.
     * @param id el id como String para validar longitud
     * @return true si válido
     */
    public static boolean isValidId(String id) {
        if (id == null) return false;
        String trimmed = id.trim();
        return DIGITS_ONLY.matcher(trimmed).matches() && trimmed.length() == 12;
    }

    /**
     * Verifica que el teléfono tenga exactamente 10 dígitos.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String trimmed = phone.trim();
        return DIGITS_ONLY.matcher(trimmed).matches() && trimmed.length() == 10;
    }

    /**
     * Verifica formato de email.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Verifica formato de fecha AAAA-MM-DD.
     */
    public static boolean isValidDate(String date) {
        if (date == null || date.isBlank()) return false;
        return DATE_PATTERN.matcher(date.trim()).matches();
    }

    /**
     * Verifica formato de hora hh:mm con minutos {00, 15, 30, 45}.
     */
    public static boolean isValidTime(String time) {
        if (time == null || time.isBlank()) return false;
        return TIME_PATTERN.matcher(time.trim()).matches();
    }

    /**
     * Verifica formato de licencia médica: L-XXXXXXXXXX MTL
     */
    public static boolean isValidLicense(String license) {
        if (license == null || license.isBlank()) return false;
        return LICENSE_PATTERN.matcher(license.trim()).matches();
    }

    /**
     * Verifica formato de oficina: O-XXX (3 caracteres alfanuméricos)
     */
    public static boolean isValidOffice(String office) {
        if (office == null || office.isBlank()) return false;
        return OFFICE_PATTERN.matcher(office.trim()).matches();
    }

    /**
     * Verifica que dos contraseñas coincidan y no estén vacías.
     */
    public static boolean passwordsMatch(String password, String confirm) {
        if (password == null || password.isBlank()) return false;
        return password.equals(confirm);
    }

    /**
     * Verifica que un String no sea nulo ni vacío.
     */
    public static boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }

    /**
     * Verifica que el username no esté vacío y no contenga espacios.
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isBlank()) return false;
        return !username.contains(" ");
    }
}
