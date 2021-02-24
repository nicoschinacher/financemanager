package helpers;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void isNotValidFormat() {
        String tempFormat = "yyyy-MM-dd";
        String tempValue = "NOT-A-DATE";
        Locale tempLocale = Locale.GERMANY;
        assertFalse(Validator.isValidFormat(tempFormat, tempValue, tempLocale));
    }
    @Test
    void isValidFormat() {
        String tempFormat = "yyyy-MM-dd";
        String tempValue = "2020-12-20";
        Locale tempLocale = Locale.GERMANY;
        assertTrue(Validator.isValidFormat(tempFormat, tempValue, tempLocale));
    }
}