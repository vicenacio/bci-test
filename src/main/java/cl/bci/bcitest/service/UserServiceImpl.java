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
import cl.bci.bcitest.security.JwtUtil;
import cl.bci.bcitest.service.model.PhoneDTO;
import cl.bci.bcitest.service.model.UserDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final String emailPattern;
  private final String passwordPattern;
  private final String secret;
  private final PhoneRepository phoneRepository;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserServiceImpl(
      final UserRepository userRepository,
      @Value("${email.pattern}") final String emailPattern,
      @Value("${password.pattern}") final String passwordPattern,
      @Value("${jwt.secret}") String secret,
      final PhoneRepository phoneRepository,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.emailPattern = emailPattern;
    this.passwordPattern = passwordPattern;
    this.secret = secret;
    this.phoneRepository = phoneRepository;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public UserDTO createUser(final UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
      throw new GenericException("El correo ingresado ya está registrado.");
    }
    validateEmailFormat(userDTO.getEmail(), emailPattern);
    validatePasswordFormat(userDTO.getPassword(), passwordPattern);

    final String token = jwtUtil.generateToken(userDTO.getName(), secret);

    final User savedUser =
        new User(
            userDTO.getName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            getSavedUserPhones(userDTO),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            token,
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
                    new Phone(null, phone.getNumber(), phone.getCitycode(), phone.getCountrycode()))
            .collect(Collectors.toList());
    return phoneRepository.saveAll(savedPhones);
  }
}
