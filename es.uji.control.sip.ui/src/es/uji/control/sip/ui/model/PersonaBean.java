	package es.uji.control.sip.ui.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.riena.beans.common.AbstractBean;
import org.eclipse.swt.graphics.Image;

import es.uji.control.sip.ui.Activator;

public class PersonaBean extends AbstractBean {
	
	private long id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private Image foto;
	
	static public PersonaBean createFromEntity(Persona persona, Foto foto) {
		PersonaBean bean = new PersonaBean();
		bean.id = persona.getId();
		bean.nombre = persona.getNombre() == null ? "" : persona.getNombre();
		bean.apellido1 = persona.getApellido1() == null ? "" : persona.getApellido1(); 
		bean.apellido2 = persona.getApellido2() == null ? "" : persona.getApellido2(); 
		bean.dni = persona.getIdentificacion() == null ? "" : persona.getIdentificacion(); 
		if (foto != null) {
			try {
				InputStream inputStream = new ByteArrayInputStream(foto.getImagen());
				Image fotoPrueba = new Image(null, inputStream);
				bean.foto = new Image(null, fotoPrueba.getImageData().scaledTo(70, 80));
			} catch (Throwable t) {
				Activator.getLogger().info("No se puede convertir la foto.");
				bean.foto = null;
			}
		}
		return bean;
	}
	
	public PersonaBean() {
		id = 0;
		nombre = "";
		apellido1 = "";
		apellido2 = "";
		dni = "";
		foto = null;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long perId) {
		this.id = perId;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido1() {
		return apellido1;
	}
	
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	
	public String getApellido2() {
		return apellido2;
	}
	
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public Image getFoto() {
		return foto;
	}
	
	public void setFoto(byte[] foto) {
		InputStream inputStream = new ByteArrayInputStream(foto);
		Image fotoPrueba = new Image(null, inputStream);
		this.foto = new Image(null, fotoPrueba.getImageData().scaledTo(70, 80));
	}
}
