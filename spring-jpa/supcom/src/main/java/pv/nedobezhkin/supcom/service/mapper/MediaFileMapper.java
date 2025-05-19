package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.MediaFile;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.service.dto.MediaFileDTO;

@Mapper(componentModel = "spring")
public interface MediaFileMapper extends EntityMapper<MediaFileDTO, MediaFile> {
	@Mapping(source = "post.id", target = "post")
	@Override
	MediaFileDTO toDto(MediaFile entity);

	@Mapping(target = "post", ignore = true)
	@Override
	MediaFile toEntity(MediaFileDTO dto);

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
}
