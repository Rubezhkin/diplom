package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserPost;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

@Mapper(componentModel = "spring")
public interface UserPostMapper extends EntityMapper<UserPostDTO, UserPost> {
	@Mapping(source = "post.id", target = "post")
	@Mapping(source = "user.id", target = "user")
	@Override
	UserPostDTO toDto(UserPost entity);

	@Mapping(target = "post", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Override
	UserPost toEntity(UserPostDTO dto);

	default Post mapPostIdToPost(Long postId) {
		if (postId == null)
			return null;
		Post post = new Post();
		post.setId(postId);
		return post;
	}

	default Long mapUserToOwnerId(Post post) {
		return post != null ? post.getId() : null;
	}

	default User mapUserIdToUser(Long userId) {
		if (userId == null)
			return null;
		User user = new User();
		user.setId(userId);
		return user;
	}

	default Long mapUserToUserId(User user) {
		return user != null ? user.getId() : null;
	}
}
