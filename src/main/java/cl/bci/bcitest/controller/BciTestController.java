package cl.bci.bcitest.controller;

import cl.bci.bcitest.service.UserService;
import cl.bci.bcitest.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class BciTestController {

  private final UserService userService;

  @Autowired
  public BciTestController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> createUserDTO(@RequestBody final UserDTO userDTO) {
    final UserDTO userDTOCreated = userService.createUser(userDTO);
    return ResponseEntity.ok().body(userDTOCreated);
  }

  @GetMapping("/findById/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
  }
  @GetMapping("/findAll")
  public ResponseEntity<List<UserDTO>> findAllUsers() {
    return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
  }


  @PutMapping("/updateById/{id}")
  public ResponseEntity<UserDTO> updateUserById(
      @PathVariable Long id, @RequestBody UserDTO userDTO) {
    userService.updateUser(id, userDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/deleteById/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/deleteAll")
  public ResponseEntity<Void> deleteAllUsers() {
    userService.deleteAllUsers();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
