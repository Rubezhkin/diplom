package pv.nedobezhkin.supcom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.UserPost;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {
	boolean existsByUserIdAndPostId(Long userId, Long postId);

	List<UserPost> findAllByUserId(Long userId);
}
