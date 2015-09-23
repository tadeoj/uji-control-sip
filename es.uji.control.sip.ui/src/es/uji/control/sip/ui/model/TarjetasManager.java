package es.uji.control.sip.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class TarjetasManager {
	
	public static final String PROPERTY_SELECTED_TARJETA = "selectedTarjeta";

	private PropertyChangeSupport propertyChangeSupport;
	private List<TarjetaBean> tarjetas;
	private TarjetaBean selectedTarjeta;

	public TarjetasManager(List<TarjetaBean> tarjetas) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		setTarjetas(tarjetas);
	}

	public TarjetaBean getLastTarjeta() {
		TarjetaBean lastTarjetaBean = null;
		for (TarjetaBean currentTarjeta : tarjetas) {
			if (lastTarjetaBean == null) {
				lastTarjetaBean = currentTarjeta;
			} else {
				if (currentTarjeta.getId() > lastTarjetaBean.getId()) {
					lastTarjetaBean = currentTarjeta;
				}
			}
		}
		return lastTarjetaBean;
	}
	
	public int getLastPosition() {
		TarjetaBean tarjeta = getLastTarjeta();
		for (int i = 0; i < tarjetas.size(); i++) {
			if (tarjetas.get(i) == tarjeta) {
				return i;
			}
		}
		return 0;
	}

	public void clear() {
		this.tarjetas.clear();
	}
	
	public void setTarjetas(List<TarjetaBean> tarjetas) {
		this.tarjetas = tarjetas;
	}
	
	public List<TarjetaBean> getTarjetas() {
		return tarjetas;
	}
	
	public TarjetaBean getSelectedTarjeta() {
		return selectedTarjeta;
	}

	public void setSelectedTarjeta(TarjetaBean newSelection) {
		TarjetaBean oldSelection = this.selectedTarjeta;
		this.selectedTarjeta = newSelection;
		propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_TARJETA, oldSelection, newSelection);
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
