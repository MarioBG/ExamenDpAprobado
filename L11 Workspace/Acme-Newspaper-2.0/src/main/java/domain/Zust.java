
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

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
	private Integer	gauge;
	private String	description;
	private Date	moment;
	private boolean	isFinal;


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

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Valid
	@Range(min = 1, max = 3)
	public Integer getGauge() {
		return this.gauge;
	}

	public void setGauge(final Integer gauge) {
		this.gauge = gauge;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}


	// Relationships

	private Newspaper	newspaper;
	private Admin		admin;


	@Valid
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}

	@Valid
	@ManyToOne(optional = false)
	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(final Admin admin) {
		this.admin = admin;
	}

}
