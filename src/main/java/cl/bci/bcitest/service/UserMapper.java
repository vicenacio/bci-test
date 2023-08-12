package cl.bci.bcitest.service;

import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;

import java.util.List;
import java.util.Objects;

import static cl.bci.bcitest.util.DateUtils.formatDate;

public class UserMapper {

  public static UserDTO mapResponseForCreateUser(final User user) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        null,
        null,
        null,
        formatDate(user.getCreated()),
        formatDate(user.getModified()),
        formatDate(user.getLastLogin()),
        user.getToken(),
        user.isActive());
  }

  public static UserDTO mapResponseForGetById(final User user, final List<PhoneDTO> userPhones) {
    if (Objects.isNull(user.getModified())) {
      return new UserDTO(
          user.getId(),
          user.getName(),
          user.getEmail(),
          null,
          userPhones,
          formatDate(user.getCreated()),
          null,
          formatDate(user.getLastLogin()),
          user.getToken(),
          user.isActive());
    }
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        null,
        userPhones,
        formatDate(user.getCreated()),
        formatDate(user.getModified()),
        formatDate(user.getLastLogin()),
        user.getToken(),
        user.isActive());
  }
}
