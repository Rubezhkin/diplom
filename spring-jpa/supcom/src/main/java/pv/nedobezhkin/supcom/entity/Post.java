package pv.nedobezhkin.supcom.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
	private Author author;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "subscription_tier_id", referencedColumnName = "id")
	private SubscriptionTier tier;

	@Column(name = "creation_date")
	private ZonedDateTime creationTime;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "content")
	private String content;
}
