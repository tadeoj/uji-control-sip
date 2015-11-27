package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.domain.provider.service.connectionfactory.IControlConnectionFactory;
import es.uji.control.model.sip.IModel;
import es.uji.control.sip.ui.Activator;

public class UpdateModelHandler {

	private IEventBroker eventBroker; 
	private IControlConnectionFactory connectionFactory;
	private IModel modelSIP;
	private Shell shell;
	private boolean updating = true;
	
	@Inject
	public UpdateModelHandler(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, IEventBroker eventBroker) {
		
		// Inyeccion
		this.shell = shell;
		this.eventBroker = eventBroker;
		
		// Servicios OSGI
		this.connectionFactory = Activator.controlConnectionFactoryServiceTracker.getConnectionFactory();
		this.modelSIP = Activator.modelSIPServiceTracker.getModelSIP();
		
		// Inicializacion del estado de la actualizacion
		modelSIP.setUpdateModelUpdatingTracker(this::setUpdating);
		
	}
	
	private void setUpdating(Boolean updating) {
		// Se actualiza el estado
		this.updating = updating;
		// Se fuerza al UI a actualizarse
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	@Execute
	public void execute() {
		
		modelSIP.updateModelFromBackend();

//		Job job = new Job("Carga del modelo") {
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				modelSIP.updateModelFromBackend();
//				return Status.OK_STATUS;
//			}
//		};
//		job.schedule();
	}

	@CanExecute
	public boolean canExecute() {
		return !updating;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		synchronized (this) {
			this.connectionFactory = connectionFactory;
		}	
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		synchronized (this) {
			this.connectionFactory = null;
		}
	}

	@Inject
	@Optional
	private void subscribeModelSIPAdded(@UIEventTopic("ADDED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = modelSIP;
		}
	}

	@Inject
	@Optional
	private void subscribeModelSIPRemoved(@UIEventTopic("REMOVED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = null;
		}
	}

}
