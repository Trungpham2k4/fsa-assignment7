package fa.training.util;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(java.time.format.ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-uuuu HH:mm:ss")
            .withResolverStyle(java.time.format.ResolverStyle.STRICT);
}
