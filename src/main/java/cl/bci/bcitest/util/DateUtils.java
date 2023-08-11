package cl.bci.bcitest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
  public static LocalDateTime formatDate(LocalDateTime dateTime) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    final String formattedDate = dateTime.format(formatter);
    return LocalDateTime.parse(formattedDate, formatter);
  }
}
