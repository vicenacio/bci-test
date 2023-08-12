package cl.bci.bcitest.service.model;

public class LoginDTO {

  private String email;
  private String password;

  public LoginDTO() {}

  public LoginDTO(final String email, final String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
