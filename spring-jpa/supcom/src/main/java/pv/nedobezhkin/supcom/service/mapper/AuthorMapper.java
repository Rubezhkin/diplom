package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.*;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

@Mapper
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
}
