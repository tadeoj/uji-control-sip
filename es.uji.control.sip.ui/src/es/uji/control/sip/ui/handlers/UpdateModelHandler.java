package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.model.sip.IModel;

public class UpdateModelHandler {
	
	@Inject
	private Logger logger;
	
	private IEventBroker eventBroker; 
	private IModel modelSIP;
	private Shell shell;
	private boolean updating = true;
	
	@Inject
	public UpdateModelHandler(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, IEventBroker eventBroker, IModel modelSIP) {
		// Inyeccion
		this.shell = shell;
		this.eventBroker = eventBroker;
		
		// Servicios OSGI
		this.modelSIP = modelSIP;
		
		// Inicializacion del estado de la actualizacion
		this.modelSIP.setUpdateModelUpdatingTracker(this::setUpdating);
	}
	
	private void setUpdating(Boolean updating) {
		// Se actualiza el estado
		this.updating = updating;
		// Se fuerza al UI a actualizarse
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	@Execute
	public void execute() {
		logger.info("Empieza la carga del modelo.");
		modelSIP.updateModelFromBackend();
	}

	@CanExecute
	public boolean canExecute() {
		return !updating;
	}

}
