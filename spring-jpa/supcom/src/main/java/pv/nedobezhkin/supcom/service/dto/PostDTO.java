package pv.nedobezhkin.supcom.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class PostDTO {
	private Long id;
	private String title;
	private Long author;
	private Long tier;
	private ZonedDateTime creationTime;
}
