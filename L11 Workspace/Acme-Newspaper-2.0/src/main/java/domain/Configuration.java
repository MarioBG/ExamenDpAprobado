
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors

	public Configuration() {
		super();
	}


	// Attributes

	private String tabooWords;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTabooWords() {
		return this.tabooWords;
	}

	public void setTabooWords(final String tabooWords) {
		this.tabooWords = tabooWords;
	}

}
