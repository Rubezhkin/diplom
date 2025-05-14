package pv.nedobezhkin.supcom.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorDTO {
	private Long id;
	@NotNull
	private Long owner;
	@NotBlank
	private String name;
	private String description;
}
