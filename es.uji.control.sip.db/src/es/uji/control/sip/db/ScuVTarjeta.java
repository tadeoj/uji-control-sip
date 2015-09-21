package es.uji.control.sip.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SCU_V_TARJETAS", schema = "GRI_PER")
public class ScuVTarjeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F_CANCELACION")
	private Date fCancelacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F_CREACION")
	private Date fCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F_EMISION")
	private Date fEmision;

	@Column(name="FICHAR")
	private Boolean fichar;

	@Column(name="ORIGEN")
	private long origen;
    
	@Column(name="PER_ID")
	private long perId;

	@Column(name="SERIAL_NUMBER")
	private long serialNumber;

	@Id
	@Column(name="TARJETA_ID")
	private long tarjetaId;

	public ScuVTarjeta() {
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFCancelacion() {
		return this.fCancelacion;
	}

	public void setFCancelacion(Date fCancelacion) {
		this.fCancelacion = fCancelacion;
	}

	public Date getFCreacion() {
		return this.fCreacion;
	}

	public void setFCreacion(Date fCreacion) {
		this.fCreacion = fCreacion;
	}

	public Date getFEmision() {
		return this.fEmision;
	}

	public void setFEmision(Date fEmision) {
		this.fEmision = fEmision;
	}

	public Boolean getFichar() {
		return this.fichar;
	}

	public void setFichar(Boolean fichar) {
		this.fichar = fichar;
	}

	public long getOrigen() {
		return this.origen;
	}

	public void setOrigen(long origen) {
		this.origen = origen;
	}

	public long getPerId() {
		return this.perId;
	}

	public void setPerId(long perId) {
		this.perId = perId;
	}

	public long getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public long getTarjetaId() {
		return this.tarjetaId;
	}

	public void setTarjetaId(long tarjetaId) {
		this.tarjetaId = tarjetaId;
	}

}