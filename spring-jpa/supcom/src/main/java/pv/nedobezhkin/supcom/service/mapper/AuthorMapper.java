package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
	@Mapping(source = "owner.id", target = "owner")
	@Override
	AuthorDTO toDto(Author entity);

	@Mapping(target = "owner", ignore = true)
	@Override
	Author toEntity(AuthorDTO dto);

	// Преобразование Long (owner ID) в User
	default User mapOwnerIdToUser(Long ownerId, @Context UserRepository userRepository) {
		if (ownerId == null)
			return null;
		return userRepository.findById(ownerId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + ownerId));
	}

	// Преобразование User в Long (owner ID)
	default Long mapUserToOwnerId(User user) {
		return user != null ? user.getId() : null;
	}
}
