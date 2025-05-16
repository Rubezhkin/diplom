package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.service.dto.SubscriptionTierDTO;

@Mapper(componentModel = "spring")
public interface SubscriptionTierMapper extends EntityMapper<SubscriptionTierDTO, SubscriptionTier> {

	@Mapping(source = "author.id", target = "author")
	@Override
	SubscriptionTierDTO toDto(SubscriptionTier entity);

	@Mapping(target = "author", ignore = true)
	@Override
	SubscriptionTier toEntity(SubscriptionTierDTO dto);

	default Author mapAuthorIdtoAuthor(Long authorId) {
		if (authorId == null)
			return null;
		Author author = new Author();
		author.setId(authorId);
		return author;
	}

	default Long mapAuthortoAuthorId(Author author) {
		return author != null ? author.getId() : null;
	}
}
