package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import es.uji.control.domain.people.ILinkage;

public class UserLinkManager {
	public static final String PROPERTY_NEW_USER_LINK = "NEW_USER_LINK";
	public static final String PROPERTY_CLEAR_USER_LINK = "CLEAR_USER_LINK";

	private PropertyChangeSupport propertyChangeSupport;
	private IObservableList collection;

	public UserLinkManager() {
		collection = new WritableList();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void clean() {
		collection.clear();
		propertyChangeSupport.firePropertyChange(PROPERTY_CLEAR_USER_LINK, collection, null);
	}

	public IObservableList getCollection() {
		return collection;
	}

	public void setUserLinksList(List<ILinkage> linkages) {
		collection.addAll(linkages);
		propertyChangeSupport.firePropertyChange(PROPERTY_NEW_USER_LINK, collection, linkages);
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
