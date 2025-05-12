package pv.nedobezhkin.supcom.service.dto;

import lombok.Data;

@Data
public class AuthorDTO {
	private Long id;
	private Long owner;
	private String name;
	private String description;
}
