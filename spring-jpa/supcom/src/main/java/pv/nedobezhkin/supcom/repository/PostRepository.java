package pv.nedobezhkin.supcom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByAuthorId(Long id);

	@Query("""
			SELECT DISTINCT p FROM Post p
			 WHERE
			     p.author IN (
			         SELECT us.tier.author FROM UserSubscription us
			         WHERE us.user = :user
			           AND (us.endDate IS NULL OR us.endDate > CURRENT_TIMESTAMP)
			     )
			     OR
			     p.author IN (
			         SELECT up.post.author FROM UserPost up
			         WHERE up.user = :user
			     )
			     OR
			     p.author.owner = :user
				ORDER by p.creationTime DESC
			""")
	List<Post> findPostBySubscriptions(User user);
}
