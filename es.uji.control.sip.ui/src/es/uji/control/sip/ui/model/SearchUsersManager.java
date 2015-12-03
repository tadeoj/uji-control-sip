package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import es.uji.control.domain.people.IPerson;

public class SearchUsersManager {
	
	public static final String PROPERTY_NEW_USERS = "NEW_EVENT_GRID";
	public static final String PROPERTY_CLEAR_USERS = "CLEAR_EVENT_GRID";
		
	private PropertyChangeSupport propertyChangeSupport;
	private IObservableList collection;

	public SearchUsersManager() {
		collection = new WritableList();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void clean() {
		collection.clear();
		propertyChangeSupport.firePropertyChange(PROPERTY_CLEAR_USERS, collection, null);
	}
	
	public IObservableList getCollection() {
		return collection;
	}
	
	public void setUsersList(List<IPerson> person) {
		collection.addAll(person);
		propertyChangeSupport.firePropertyChange(PROPERTY_NEW_USERS, collection, person);
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
