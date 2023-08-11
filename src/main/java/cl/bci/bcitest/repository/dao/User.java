package cl.bci.bcitest.repository.dao;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String email;
  private String password;

  @OneToMany private List<Phone> phones;

  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private boolean isActive;

  public User() {}

  public User(
      String name,
      String email,
      String password,
      List<Phone> phones,
      LocalDateTime created,
      LocalDateTime modified,
      LocalDateTime lastLogin,
      String token,
      boolean isActive) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
    this.created = created;
    this.modified = modified;
    this.lastLogin = lastLogin;
    this.token = token;
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public String getToken() {
    return token;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
