package fa.training.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static fa.training.util.Constant.DATE_FORMATTER;

public class Validator {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isValidDate(String date) {
        try{
            LocalDate.parse(date, DATE_FORMATTER);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

    public static boolean isValidDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, Constant.DATE_TIME_FORMATTER);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return !email.isBlank() && email.matches(Constant.EMAIL_REGEX);
    }

    public static boolean isValidAction(String action) {
        return isNotBlank(action) && (action.equals("IN") || action.equals("OUT"));
    }

    public static boolean isValidPositiveNumber(double number) {
        return number > 0;
    }
}