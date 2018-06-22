
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Zust extends DomainEntity {

	// Constructors

	public Zust() {
		super();
	}


	// Attributes

	private String	title;
	private String	ticker;
	private Integer	klok;
	private String	description;


	@Valid
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Valid
	@Pattern(regexp = "^\\d{6}-\\d{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@Range(min = 1, max = 3)
	public Integer getKlok() {
		return this.klok;
	}

	public void setKlok(Integer klok) {
		this.klok = klok;
	}

	@Valid
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	// Relationships

	private Newspaper	newspaper;


	@Valid
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
