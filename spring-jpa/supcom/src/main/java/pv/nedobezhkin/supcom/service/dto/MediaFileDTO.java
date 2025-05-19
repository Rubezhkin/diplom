package pv.nedobezhkin.supcom.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;
import pv.nedobezhkin.supcom.entity.enums.FileType;

@Data
public class MediaFileDTO {
	private Long id;
	private String name;
	private FileType type;
	private String path;
	private Long post;
	private ZonedDateTime uploadedAt;
}
