package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class PersonasManager {
	public static final String PROPERTY_SELECTED_PERSON = "selectedPerson";

	private PropertyChangeSupport propertyChangeSupport;
	private List<PersonaBean> personas;
	private PersonaBean selectedPerson;

	public PersonasManager(List<PersonaBean> personas) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		setPersonas(personas);
	}

	public void clean() {
		this.personas.clear();
	}
	
	public void setPersonas(List<PersonaBean> personas) {
		this.personas = personas;
	}
	
	public List<PersonaBean> getPersonas() {
		return this.personas;
	}
	
	public PersonaBean getSelectedPerson() {
		return selectedPerson;
	}
	
	public void setSelectedPerson(PersonaBean newSelection) {
		PersonaBean oldSelection = this.selectedPerson;
		this.selectedPerson = newSelection;
		propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_PERSON, oldSelection, newSelection);
	}
	
	
	public void addPropertyChangeListener(String p, PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(p, l);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String p, PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(p, l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}
}
