package cl.bci.bcitest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(GenericException.class)
  public ResponseEntity<ExceptionResponse> handleEmailExistsException(GenericException exception) {
    ExceptionResponse ex = new ExceptionResponse(exception.getMessage());
    return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
  }
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception) {
    ExceptionResponse ex = new ExceptionResponse(exception.getMessage());
    return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
  }
}
