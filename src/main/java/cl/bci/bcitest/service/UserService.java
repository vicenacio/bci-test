package cl.bci.bcitest.service;

import cl.bci.bcitest.service.model.UserDTO;

public interface UserService {

    UserDTO createUser(final UserDTO userDTO);
    
    UserDTO getUserById(final Long id);

    void updateUser(final UserDTO userDTO);

    void deleteUserById(final Long id);


}
