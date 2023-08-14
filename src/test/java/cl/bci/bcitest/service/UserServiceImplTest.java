package cl.bci.bcitest.service;

import cl.bci.bcitest.repository.PhoneRepository;
import cl.bci.bcitest.repository.UserRepository;
import cl.bci.bcitest.repository.dao.User;
import cl.bci.bcitest.security.JwtUtil;
import cl.bci.bcitest.service.model.LoginDTO;
import cl.bci.bcitest.service.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
  @Mock private UserRepository userRepository;
  @Mock private PhoneRepository phoneRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtUtil jwtUtil;

  private static final String USER_EMAIL = "vicente@vicente.cl";
  private static final String USER_PASSWEORD = "Passw0rd";
  private UserServiceImpl userService;
  private static final String REGEX_PATTERN = "pattern";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userService =
        new UserServiceImpl(
            userRepository,
            REGEX_PATTERN,
            REGEX_PATTERN,
            phoneRepository,
            passwordEncoder,
            jwtUtil);
  }

  @Test
  public void itShouldReturnLoginOK_whenUserExistsAndEmailAndPasswordMatchesWithPattern() {
    final LoginDTO loginDTO = new LoginDTO(USER_EMAIL, USER_PASSWEORD);
    final User existingUser =
        new User(
            "vicente",
            "vicente@vicente.cl",
            "Passw0rd",
            Collections.emptyList(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "token123",
            true);
    when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(existingUser));
    when(passwordEncoder.matches(loginDTO.getEmail(), existingUser.getEmail())).thenReturn(true);
    when(passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword()))
        .thenReturn(true);

    final UserDTO serviceResponse = userService.doLogin(loginDTO);

    Assertions.assertNotNull(serviceResponse);
  }

  @Test
  public void itShouldReturnAnyUserById_whenUserExistsById() {
    final User existingUser =
        new User(
            "vicente",
            "vicente@vicente.cl",
            "Passw0rd",
            Collections.emptyList(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "token123",
            true);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

    final UserDTO serviceResponse = userService.findUserById(1L);

    Assertions.assertNotNull(serviceResponse);
  }

  @Test
  public void itShouldReturnAllUserList() {
    final List<User> userList =
        Collections.singletonList(
            new User(
                "vicente",
                "vicente@vicente.cl",
                "Passw0rd",
                Collections.emptyList(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "token123",
                true));

    when(userRepository.findAll()).thenReturn(userList);

    final List<UserDTO> serviceResponse = userService.findAllUsers();

    Assertions.assertNotNull(serviceResponse);
  }
}
