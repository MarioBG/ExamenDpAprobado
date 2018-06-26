
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "publisher_id")
})
public class Newspaper extends DomainEntity {

	// Constructors

	public Newspaper() {
		super();
	}


	// Attributes

	private String	title;
	private String	description;
	private Date	publicationDate;
	private String	picture;
	private boolean	isPrivate;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public boolean getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
	}


	// Relationships

	private User								publisher;
	private Collection<Article>					articles;
	private Collection<SubscriptionNewspaper>	subscriptionsNewspaper;
	private Collection<Advertisement>			advertisements;
	private Collection<Volume>					volumes;
	private Collection<Zust>					zusts;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final User publisher) {
		this.publisher = publisher;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "newspaper")
	public Collection<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "newspaper")
	public Collection<SubscriptionNewspaper> getSubscriptionsNewspaper() {
		return this.subscriptionsNewspaper;
	}

	public void setSubscriptionsNewspaper(final Collection<SubscriptionNewspaper> subscriptionsNewspaper) {
		this.subscriptionsNewspaper = subscriptionsNewspaper;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "newspaper")
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Volume> getVolumes() {
		return this.volumes;
	}

	public void setVolumes(final Collection<Volume> volumes) {
		this.volumes = volumes;
	}

	@OneToMany(mappedBy = "newspaper")
	public Collection<Zust> getZusts() {
		return this.zusts;
	}

	public void setZusts(final Collection<Zust> zusts) {
		this.zusts = zusts;
	}

}
