package pv.nedobezhkin.supcom.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorDTO {
	private Long id;
	private Long owner;
	@NotBlank
	private String name;
	private String description;
}
