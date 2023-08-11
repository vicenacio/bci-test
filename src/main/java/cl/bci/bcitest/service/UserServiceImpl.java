package cl.bci.bcitest.service;

import static cl.bci.bcitest.service.UserMapper.mapResponseForCreateUser;
import static cl.bci.bcitest.service.UserMapper.mapResponseForGetById;
import static cl.bci.bcitest.util.ValidationUtils.validateEmailFormat;
import static cl.bci.bcitest.util.ValidationUtils.validatePasswordFormat;

import cl.bci.bcitest.exception.GenericException;
import cl.bci.bcitest.repository.PhoneRepository;
import cl.bci.bcitest.repository.UserRepository;
import cl.bci.bcitest.repository.dao.Phone;
import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PhoneRepository phoneRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository) {
    this.userRepository = userRepository;
    this.phoneRepository = phoneRepository;
  }

  @Override
  public UserDTO createUser(final UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
      throw new GenericException("El correo ingresado ya está registrado.");
    }
    validateEmailFormat(userDTO.getEmail());
    validatePasswordFormat(userDTO.getPassword());

    final User savedUser =
        new User(
            userDTO.getName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            getSavedUserPhones(userDTO),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "",
            true);

    userRepository.save(savedUser);
    return mapResponseForCreateUser(savedUser);
  }

  @Override
  public UserDTO getUserById(final Long id) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("El usuario consultado no existe."));

    List<PhoneDTO> userPhones =
        user.getPhones().stream()
            .map(
                phone ->
                    new PhoneDTO(
                        null, phone.getNumber(), phone.getCityCode(), phone.getCountrycode()))
            .collect(Collectors.toList());

    return mapResponseForGetById(user, userPhones);
  }

  @Override
  public void updateUser(final UserDTO userDTO) {}

  @Override
  public void deleteUserById(final Long id) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("El usuario consultado no existe."));
    userRepository.deleteById(user.getId());
  }

  private List<Phone> getSavedUserPhones(final UserDTO userDTO) {
    final List<Phone> savedPhones =
        userDTO.getPhones().stream()
            .map(
                phone ->
                    new Phone(null, phone.getNumber(), phone.getCitycode(), phone.getCountrycode()))
            .collect(Collectors.toList());
    return phoneRepository.saveAll(savedPhones);
  }
}
