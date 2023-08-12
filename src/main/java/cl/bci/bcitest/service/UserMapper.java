package cl.bci.bcitest.service;

import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;

import java.util.List;

import static cl.bci.bcitest.util.DateUtils.formatDate;

import java.util.stream.Collectors;

public class UserMapper {

  public static UserDTO mapResponseUser(final User user) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        null,
        null,
        null,
        formatDate(user.getCreated()),
        user.getModified() != null ? formatDate(user.getModified()) : null,
        formatDate(user.getLastLogin()),
        user.getToken(),
        user.isActive());
  }

  public static UserDTO mapResponseForCreateUser(final User user) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        null,
        null,
        null,
        formatDate(user.getCreated()),
        user.getModified() != null ? formatDate(user.getModified()) : null,
        user.getLastLogin() != null ? formatDate(user.getLastLogin()) : null,
        user.getToken(),
        user.isActive());
  }

  public static UserDTO mapResponseForGetById(final User user, final List<PhoneDTO> userPhones) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        null,
        userPhones,
        formatDate(user.getCreated()),
        user.getModified() != null ? formatDate(user.getModified()) : null,
        user.getLastLogin() != null ? formatDate(user.getLastLogin()) : null,
        null,
        user.isActive());
  }

  public static List<UserDTO> mapUserDTOForFindAll(final List<User> userEntityList) {
    return userEntityList.stream()
        .map(
            userEntity -> {
              List<PhoneDTO> phones =
                  userEntity.getPhones().stream()
                      .map(
                          phone ->
                              new PhoneDTO(
                                  null,
                                  phone.getNumber(),
                                  phone.getCityCode(),
                                  phone.getCountryCode()))
                      .collect(Collectors.toList());
              return new UserDTO(
                  userEntity.getId(),
                  userEntity.getName(),
                  userEntity.getEmail(),
                  null,
                  phones,
                  userEntity.getCreated(),
                  userEntity.getModified(),
                  userEntity.getLastLogin(),
                  userEntity.getToken(),
                  userEntity.isActive());
            })
        .collect(Collectors.toList());
  }
}
