package pv.nedobezhkin.supcom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
	private User owner;

	@Column(unique = true, name = "name", nullable = false)
	private String name;

	@Column(name = "descriprion")
	private String description;
}
