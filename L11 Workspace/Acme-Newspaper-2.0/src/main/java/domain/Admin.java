
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Admin extends Actor {

	// Constructors

	public Admin() {
		super();
	}


	// Attributes

	// Relationships

	private Collection<Zust>	zusts;


	@Valid
	@OneToMany(mappedBy = "admin")
	public Collection<Zust> getZusts() {
		return this.zusts;
	}

	public void setZusts(final Collection<Zust> zusts) {
		this.zusts = zusts;
	}

}
