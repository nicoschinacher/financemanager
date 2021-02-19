package helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Validator {

    public static boolean isValidFormat(String format, String value, Locale locale) {
        LocalDateTime localDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        try {
            localDateTime = LocalDateTime.parse(value, formatter);
            String result = localDateTime.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException exception0) {
            try {
                LocalDate ld = LocalDate.parse(value, formatter);
                String result = ld.format(formatter);
                return result.equals(value);
            } catch (DateTimeParseException exception1) {
                try {
                    LocalTime lt = LocalTime.parse(value, formatter);
                    String result = lt.format(formatter);
                    return result.equals(value);
                } catch (DateTimeParseException exception2) {
                    System.err.println(
                            exception0.getMessage()
                                    + exception1.getMessage()
                                    + exception2.getMessage()
                    );
                }
            }
        }
        return false;
    }
}