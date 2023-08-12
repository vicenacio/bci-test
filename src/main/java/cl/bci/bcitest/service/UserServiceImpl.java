package cl.bci.bcitest.service;

import static cl.bci.bcitest.service.UserMapper.*;
import static cl.bci.bcitest.util.ValidationUtils.validateEmailFormat;
import static cl.bci.bcitest.util.ValidationUtils.validatePasswordFormat;

import cl.bci.bcitest.exception.GenericException;
import cl.bci.bcitest.exception.UserNotFoundException;
import cl.bci.bcitest.repository.PhoneRepository;
import cl.bci.bcitest.repository.UserRepository;
import cl.bci.bcitest.repository.dao.Phone;
import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.security.JwtUtil;
import cl.bci.bcitest.service.model.LoginDTO;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final String emailPattern;
  private final String passwordPattern;
  private final PhoneRepository phoneRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserServiceImpl(
      final UserRepository userRepository,
      @Value("${email.pattern}") final String emailPattern,
      @Value("${password.pattern}") final String passwordPattern,
      final PhoneRepository phoneRepository,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.emailPattern = emailPattern;
    this.passwordPattern = passwordPattern;
    this.phoneRepository = phoneRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public UserDTO doLogin(final LoginDTO loginDTO) {
    final User existingUser =
        userRepository
            .findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new UserNotFoundException("El usuario no está registrado"));
    if (passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword())
        || !Objects.isNull(loginDTO.getPassword())
            && existingUser.getPassword().equals(loginDTO.getPassword())) {
      existingUser.setToken(jwtUtil.generateToken(existingUser.getName()));
      existingUser.setLastLogin(LocalDateTime.now());
      userRepository.save(existingUser);

      return mapResponseUser(existingUser);
    }

    throw new UserNotFoundException("La contraseña no es válida");
  }

  @Override
  public UserDTO createUser(final UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
      throw new GenericException("El correo ingresado ya está registrado.");
    }
    validateEmailFormat(userDTO.getEmail(), emailPattern);
    validatePasswordFormat(userDTO.getPassword(), passwordPattern);

    final User savedUser =
        new User(
            userDTO.getName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            getSavedUserPhones(userDTO),
            LocalDateTime.now(),
            null,
            null,
            null,
            true);

    userRepository.save(savedUser);
    return mapResponseForCreateUser(savedUser);
  }

  @Override
  public UserDTO findUserById(final Long id) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("El usuario consultado no existe."));

    final List<PhoneDTO> userPhones =
        user.getPhones().stream()
            .map(
                phone ->
                    new PhoneDTO(
                        null, phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
            .collect(Collectors.toList());

    return mapResponseForGetById(user, userPhones);
  }

  @Override
  public List<UserDTO> findAllUsers() {
    final List<User> userEntityList = (List<User>) userRepository.findAll();

    return mapUserDTOForFindAll(userEntityList);
  }

  @Override
  public void updateUser(final Long id, final UserDTO updatedUser) {
    final User existingUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("El usuario consultado no existe."));
    final boolean isEmailValid = validateEmailFormat(updatedUser.getEmail(), emailPattern);
    final boolean isPasswordValid =
        validatePasswordFormat(updatedUser.getPassword(), passwordPattern);

    if (isEmailValid && isPasswordValid) {
      existingUser.setName(updatedUser.getName());
      existingUser.setEmail(updatedUser.getEmail());
      existingUser.setPassword(updatedUser.getPassword());
      existingUser.setPhones(getSavedUserPhones(updatedUser));
      existingUser.setModified(LocalDateTime.now());
      userRepository.save(existingUser);
    }
  }

  @Override
  public void deleteUserById(final Long id) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new GenericException("El usuario consultado no existe."));
    userRepository.deleteById(user.getId());
  }

  @Override
  public void deleteAllUsers() {
    userRepository.deleteAll();
  }

  private List<Phone> getSavedUserPhones(final UserDTO userDTO) {
    final List<Phone> savedPhones =
        userDTO.getPhones().stream()
            .map(
                phone ->
                    new Phone(null, phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
            .collect(Collectors.toList());
    return phoneRepository.saveAll(savedPhones);
  }
}
