
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	// Constructor ----------------------------------------------------------------------------
	public Folder() {
		super();
	}


	// Attributes ------------------------------------------------------------------------------
	private String	name;
	private boolean	predefined;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getPredefined() {
		return this.predefined;
	}

	public void setPredefined(final boolean predefined) {
		this.predefined = predefined;
	}


	// RelashionShips ----------------------------------------------------------------

	private Folder				parent;
	private Collection<Folder>	children;
	private Collection<Message>	messages;


	@Valid
	@ManyToOne(optional = true)
	public Folder getParent() {
		return this.parent;
	}

	public void setParent(final Folder parent) {
		this.parent = parent;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Folder> getChildren() {
		return this.children;
	}

	public void setChildren(final Collection<Folder> children) {
		this.children = children;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "folder")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}
}
