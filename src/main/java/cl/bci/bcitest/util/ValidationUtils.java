package cl.bci.bcitest.util;

import cl.bci.bcitest.exception.GenericException;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    public static void validateEmailFormat(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new GenericException("El correo no cumple con el formato correcto.");
        }
    }

    public static void validatePasswordFormat(final String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new GenericException("La contraseña no cumple con el formato correcto.");
        }
    }
}
