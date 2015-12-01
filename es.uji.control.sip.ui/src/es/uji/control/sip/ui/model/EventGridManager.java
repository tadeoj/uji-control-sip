package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class EventGridManager {
	
	public static final String PROPERTY_NEW_EVENT_GRID = "NEW_EVENT_GRID";
	public static final String PROPERTY_CLEAR_EVENT_GRID = "CLEAR_EVENT_GRID";
	public static final String PROPERTY_SIZE_EVENT_GRID = "SIZE_EVENT_GRID";
		
	private PropertyChangeSupport propertyChangeSupport;
	private List<EventGridEntry> eventGridList;
	private int size = 40;

	public EventGridManager(List<EventGridEntry> eventGridList) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		setCollection(eventGridList);
	}

	public void clean() {
		eventGridList.clear();
		propertyChangeSupport.firePropertyChange(PROPERTY_CLEAR_EVENT_GRID, eventGridList, null);
	}
	
	public List<EventGridEntry> getCollection() {
		return eventGridList;
	}
	
	public void setCollection(List<EventGridEntry> eventGridList) {
		this.eventGridList = eventGridList;
	}
	
	public int getSizeCollection() {
		return size;
	}
	
	public void setSizeCollection(int size) {
		this.size = size;
		propertyChangeSupport.firePropertyChange(PROPERTY_SIZE_EVENT_GRID, eventGridList, null);
	}
	
	public void addEventGridEntry(EventGridEntry eventGrid) {
		if (eventGridList.size() < size) {
			eventGridList.add(eventGrid);
			propertyChangeSupport.firePropertyChange(PROPERTY_NEW_EVENT_GRID, eventGridList, eventGrid);
		} else {
			eventGridList.remove(0);
			eventGridList.add(eventGrid);
			propertyChangeSupport.firePropertyChange(PROPERTY_NEW_EVENT_GRID, eventGridList, eventGrid);
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
