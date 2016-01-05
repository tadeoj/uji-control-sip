package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;

import es.uji.control.model.sip.IModel;

public class UpdatePhotosHandler {
	
	private IEventBroker eventBroker; 
	private IModel model;
	private boolean updating = true;
	
	@Inject
	public UpdatePhotosHandler(IEventBroker eventBroker, IModel model) {
		// Inyeccion
		this.eventBroker = eventBroker;
		
		// Servicios OSGI
		this.model = model;
		
		// Inicializacion del estado de la actualizacion
		this.model.setUpdatePhotosUpdatingTracker(this::setUpdating);
		
	}
	
	private void setUpdating(Boolean updating) {
		// Se actualiza el estado
		this.updating = updating;
		// Se fuerza al UI a actualizarse
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	@Execute
	public void execute() {
		model.updatePhotosFromBackend();
	}

	@CanExecute
	public boolean canExecute() {
		return !updating;
	}
	
}
