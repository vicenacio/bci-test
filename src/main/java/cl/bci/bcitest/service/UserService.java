package cl.bci.bcitest.service;

import cl.bci.bcitest.service.model.LoginDTO;
import cl.bci.bcitest.service.model.UserDTO;

import java.util.List;

public interface UserService {

  UserDTO doLogin(final LoginDTO loginDTO);

  UserDTO createUser(final UserDTO userDTO);

  UserDTO findUserById(final Long id);

  List<UserDTO> findAllUsers();

  void updateUser(final Long id, final UserDTO userDTO);

  void deleteUserById(final Long id);

  void deleteAllUsers();
}
