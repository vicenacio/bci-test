package cl.bci.bcitest.controller;

import cl.bci.bcitest.service.UserService;
import cl.bci.bcitest.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class BciTestController {

  private final UserService userService;

  @Autowired
  public BciTestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> createUserDTO(@RequestBody final UserDTO userDTO) {
    return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
  }

  @GetMapping("/findById/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @DeleteMapping("/deleteById/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
