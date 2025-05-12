package pv.nedobezhkin.supcom.service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SubscriptionTierDTO {
	private Long Id;
	private Long author;
	private String name;
	private String description;
	private BigDecimal price;
}
