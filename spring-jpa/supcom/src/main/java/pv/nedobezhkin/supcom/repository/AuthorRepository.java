package pv.nedobezhkin.supcom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Optional<Author> findByOwner(User owner);

	void deleteByOwnerId(Long id);
}
