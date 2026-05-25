package packagee.util;

import java.util.regex.Pattern;

public final class Validator {

    private Validator() {}

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern LICENSE_PATTERN =
            Pattern.compile("^L-\\d{10} MTL$");

    private static final Pattern OFFICE_PATTERN =
            Pattern.compile("^O-[A-Za-z0-9]{3}$");

    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    private static final Pattern TIME_PATTERN =
            Pattern.compile("^([01]\\d|2[0-3]):(00|15|30|45)$");

    private static final Pattern DIGITS_ONLY = Pattern.compile("^\\d+$");

    public static boolean isValidId(String id) {
        if (id == null) return false;
        String trimmed = id.trim();
        return DIGITS_ONLY.matcher(trimmed).matches() && trimmed.length() == 12;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String trimmed = phone.trim();
        return DIGITS_ONLY.matcher(trimmed).matches() && trimmed.length() == 10;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidDate(String date) {
        if (date == null || date.isBlank()) return false;
        return DATE_PATTERN.matcher(date.trim()).matches();
    }

    public static boolean isValidTime(String time) {
        if (time == null || time.isBlank()) return false;
        return TIME_PATTERN.matcher(time.trim()).matches();
    }

    public static boolean isValidLicense(String license) {
        if (license == null || license.isBlank()) return false;
        return LICENSE_PATTERN.matcher(license.trim()).matches();
    }

    public static boolean isValidOffice(String office) {
        if (office == null || office.isBlank()) return false;
        return OFFICE_PATTERN.matcher(office.trim()).matches();
    }

    public static boolean passwordsMatch(String password, String confirm) {
        if (password == null || password.isBlank()) return false;
        return password.equals(confirm);
    }

    public static boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.isBlank()) return false;
        return !username.contains(" ");
    }
}
