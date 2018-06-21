package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {

	// Constructors

	public Volume() {
		super();
	}

	// Attributes

	private String title;
	private String description;
	private String year;

	// Relationships

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	@Pattern(regexp = "^\\d{4}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	// Relationships

	private User user;
	private Collection<Newspaper> newspapers;
	private Collection<SubscriptionVolume> subscriptionsVolume;

	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "volumes")
	public Collection<Newspaper> getNewspapers() {
		return newspapers;
	}

	public void setNewspapers(Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "volume")
	public Collection<SubscriptionVolume> getSubscriptionsVolume() {
		return subscriptionsVolume;
	}

	public void setSubscriptionsVolume(
			Collection<SubscriptionVolume> subscriptionsVolume) {
		this.subscriptionsVolume = subscriptionsVolume;
	}

}
