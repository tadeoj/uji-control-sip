package es.uji.control.sip.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PerVwPersonasSubvinculoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID")
	private long id;

	@Column(name = "SVI_ID")
	private long sviId;

	public PerVwPersonasSubvinculoPK() {
	}

	public long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public long getSviId() {
		return sviId;
	}

	public void setSviId(final long sviId) {
		this.sviId = sviId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (sviId ^ (sviId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerVwPersonasSubvinculoPK other = (PerVwPersonasSubvinculoPK) obj;
		if (id != other.id)
			return false;
		if (sviId != other.sviId)
			return false;
		return true;
	}

}
