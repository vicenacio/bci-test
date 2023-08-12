package cl.bci.bcitest.repository;

import cl.bci.bcitest.repository.dao.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(final String email);

  Optional<User> findByName(final String name);
}
