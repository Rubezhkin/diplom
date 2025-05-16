package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
	@Mapping(source = "owner.id", target = "owner")
	@Override
	AuthorDTO toDto(Author entity);

	@Mapping(target = "owner", ignore = true)
	@Override
	Author toEntity(AuthorDTO dto);

	default User mapOwnerIdToUser(Long ownerId) {
		if (ownerId == null)
			return null;
		User user = new User();
		user.setId(ownerId);
		return user;
	}

	default Long mapUserToOwnerId(User user) {
		return user != null ? user.getId() : null;
	}
}
