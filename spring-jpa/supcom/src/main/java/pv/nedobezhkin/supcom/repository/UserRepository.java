package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
