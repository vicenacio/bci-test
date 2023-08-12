package cl.bci.bcitest.security;

import cl.bci.bcitest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final cl.bci.bcitest.repository.dao.User userEntity =
        userRepository
            .findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("el usuario no ha sido encontrado."));

    return User.builder()
        .username(userEntity.getName())
        .password(userEntity.getPassword())
        .roles("ADMIN")
        .build();
  }
}
