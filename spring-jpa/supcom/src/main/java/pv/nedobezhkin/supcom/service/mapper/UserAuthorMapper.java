package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.UserAuthor;
import pv.nedobezhkin.supcom.service.dto.UserAuthorDTO;

@Mapper(componentModel = "spring")
public interface UserAuthorMapper {

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "author.id", target = "authorId")
	UserAuthorDTO toDto(UserAuthor entity);

	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "authorId", target = "author.id")
	UserAuthor toEntity(UserAuthorDTO dto);
}
