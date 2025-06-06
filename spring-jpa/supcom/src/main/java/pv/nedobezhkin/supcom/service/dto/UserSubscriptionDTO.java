package pv.nedobezhkin.supcom.service.dto;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class UserSubscriptionDTO {
	private Long id;
	private Long user;
	private Long tier;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
}
