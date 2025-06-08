package pv.nedobezhkin.supcom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserAuthor;

@Repository
public interface UserAuthorRepository extends JpaRepository<UserAuthor, Long> {
	List<UserAuthor> findByUser(User user);

	Optional<UserAuthor> findByUserAndAuthor(User user, Author author);

	Optional<UserAuthor> findAllByUser(User user);
}
