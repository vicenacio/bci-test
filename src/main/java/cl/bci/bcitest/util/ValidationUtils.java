package cl.bci.bcitest.util;

import cl.bci.bcitest.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

  public static boolean validateEmailFormat(final String email, final String emailPattern) {
    if (email.matches(emailPattern)) {
      return true;
    }
    throw new BadRequestException("El correo no cumple con el formato correcto.");
  }

  public static boolean validatePasswordFormat(
      final String password, final String passwordPattern) {
    if (password.matches(passwordPattern)) {
      return true;
    }
    throw new BadRequestException("La contraseña no cumple con el formato correcto.");
  }
}
