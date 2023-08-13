package cl.bci.bcitest.security;

import cl.bci.bcitest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    final cl.bci.bcitest.repository.dao.User userEntity =
        userRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new UsernameNotFoundException("el usuario no ha sido encontrado."));

    return new org.springframework.security.core.userdetails.User(
        userEntity.getName(), userEntity.getPassword(), Collections.emptyList());
  }
}
