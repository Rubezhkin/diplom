package pv.nedobezhkin.supcom.service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String email;
	private String password;
	private boolean isAdmin;
	private BigDecimal balance;
}
