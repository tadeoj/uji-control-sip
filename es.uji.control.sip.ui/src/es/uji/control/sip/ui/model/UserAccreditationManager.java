package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import es.uji.control.domain.people.IAccreditationInfo;

public class UserAccreditationManager {
	public static final String PROPERTY_NEW_USER_ACCREDITATION = "NEW_USER_ACCREDITATION";
	public static final String PROPERTY_CLEAR_USER_ACCREDITATION = "CLEAR_USER_ACCREDITATION";

	private PropertyChangeSupport propertyChangeSupport;
	private IObservableList collection;

	public UserAccreditationManager() {
		collection = new WritableList();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void clean() {
		collection.clear();
		propertyChangeSupport.firePropertyChange(PROPERTY_CLEAR_USER_ACCREDITATION, collection, null);
	}

	public IObservableList getCollection() {
		return collection;
	}

	public void setUserAccreditationsList(List<IAccreditationInfo> accreditations) {
		collection.addAll(accreditations);
		propertyChangeSupport.firePropertyChange(PROPERTY_NEW_USER_ACCREDITATION, collection, accreditations);
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
