package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {

	@Mapping(source = "author.id", target = "author")
	@Mapping(source = "tier.id", target = "tier")
	@Override
	PostDTO toDto(Post entity);

	@Mapping(target = "author", ignore = true)
	@Mapping(target = "tier")
	@Override
	Post toEntity(PostDTO dto);

	default Author mapAuthorIdToAuthor(Long authorId) {
		if (authorId == null)
			return null;
		Author author = new Author();
		author.setId(authorId);
		return author;
	}

	default Long mapAuthorToAuthorId(Author author) {
		return author != null ? author.getId() : null;
	}

	default SubscriptionTier mapTierIdToTier(Long tierId) {
		if (tierId == null)
			return null;
		SubscriptionTier tier = new SubscriptionTier();
		tier.setId(tierId);
		return tier;
	}

	default Long mapTierToTierId(SubscriptionTier tier) {
		return tier != null ? tier.getId() : null;
	}
}