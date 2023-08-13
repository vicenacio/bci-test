package cl.bci.bcitest.controller;

import cl.bci.bcitest.service.UserService;
import cl.bci.bcitest.service.model.LoginDTO;
import cl.bci.bcitest.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> doLogin(@RequestBody LoginDTO loginDTO) {
    final UserDTO userDTOLogged = userService.doLogin(loginDTO);

    final HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.AUTHORIZATION, userDTOLogged.getToken());
    return ResponseEntity.ok().headers(header).body(userDTOLogged);
  }
}
