package pv.nedobezhkin.supcom.service.dto;

import lombok.Data;

@Data
public class TierTierDTO {
	private Long id;
	private Long parentTier;
	private Long childTier;
}
