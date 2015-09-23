package es.uji.control.sip.ui.model;

import java.util.Date;

import org.eclipse.riena.beans.common.AbstractBean;

public class TarjetaBean extends AbstractBean {

	private long id;	
	private Persona persona;
	private Date fechaCancelacion;
	private Date fechaCreacion;
	private Date fechaEmision;
	private String descripcion;
	private boolean fichar;
	private long serialNumber;
	private long origen;
	
	static public TarjetaBean createFromEntity(ScuVTarjetas tarjeta, Persona persona) {
		TarjetaBean bean = new TarjetaBean();
		bean.setId(tarjeta.getTarjetaId());
		bean.setPersona(persona);
		bean.setFechaCancelacion(tarjeta.getFCancelacion());
		bean.setFechaCreacion(tarjeta.getFCreacion());
		bean.setFechaEmision(tarjeta.getFEmision());
		bean.setDescripcion(tarjeta.getDescripcion());
		bean.setSerialNumber(tarjeta.getSerialNumber());
		bean.setOrigen(tarjeta.getOrigen());
		if (tarjeta.isFichar() == null) {
			bean.fichar = false;
		} else {
			bean.fichar = tarjeta.isFichar();
		}
		return bean;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date cancelacion) {
		this.fechaCancelacion = cancelacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date creacion) {
		this.fechaCreacion = creacion;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date emision) {
		this.fechaEmision = emision;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public boolean isFichar() {
		return fichar;
	}
	
	public void setFichar(Boolean fichar) {
		this.fichar = fichar;
	}
	
	public long getOrigen() {
		return origen;
	}
	
	public void setOrigen(long origen) {
		this.origen = origen;
	}
	
}
