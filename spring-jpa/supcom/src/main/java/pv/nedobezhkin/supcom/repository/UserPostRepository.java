package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.UserPost;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {

}
