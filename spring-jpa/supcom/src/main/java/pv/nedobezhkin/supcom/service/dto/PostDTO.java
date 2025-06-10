package pv.nedobezhkin.supcom.service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class PostDTO {
	private Long id;
	private String title;
	private Long author;
	private Long tier;
	private ZonedDateTime creationTime;
	private BigDecimal price;
	private String content;
	private boolean access;
	private String authorName;
}
