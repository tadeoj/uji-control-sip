package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

public class EventGridManager {
	
	public static final String PROPERTY_NEW_EVENT_GRID = "NEW_EVENT_GRID";
	public static final String PROPERTY_CLEAR_EVENT_GRID = "CLEAR_EVENT_GRID";
	public static final String PROPERTY_SIZE_EVENT_GRID = "SIZE_EVENT_GRID";
		
	private PropertyChangeSupport propertyChangeSupport;
	private IObservableList collection;
	private int size = 500;

	public EventGridManager() {
		collection = new WritableList();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void clean() {
		collection.clear();
		propertyChangeSupport.firePropertyChange(PROPERTY_CLEAR_EVENT_GRID, collection, null);
	}
	
	public IObservableList getCollection() {
		return collection;
	}
	
	public int getSizeCollection() {
		return size;
	}
	
	public void setSizeCollection(int size) {
		this.size = size;
		propertyChangeSupport.firePropertyChange(PROPERTY_SIZE_EVENT_GRID, collection, null);
	}
	
	public int getLastElementPosition() {
		return collection.size()-1;
	}
	
	public void addEventGridEntry(EventGridEntry eventGrid) {
		if (collection.size() < size) {
			collection.add(eventGrid);
			propertyChangeSupport.firePropertyChange(PROPERTY_NEW_EVENT_GRID, collection, eventGrid);
		} else {
			collection.remove(0);
			collection.add(eventGrid);
			propertyChangeSupport.firePropertyChange(PROPERTY_NEW_EVENT_GRID, collection, eventGrid);
		}
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
