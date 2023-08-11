package cl.bci.bcitest.repository;

import cl.bci.bcitest.repository.dao.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {}
