package cl.bci.bcitest.service;

import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;

import java.util.List;

import static cl.bci.bcitest.util.DateUtils.formatDate;

public class UserMapper {

  public static UserDTO mapResponseForCreateUser(final User user) {
    return new UserDTO(
        user.getId(),
        null,
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
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPassword(),
        userPhones,
        formatDate(user.getCreated()),
        formatDate(user.getModified()),
        formatDate(user.getLastLogin()),
        user.getToken(),
        user.isActive());
  }
}
