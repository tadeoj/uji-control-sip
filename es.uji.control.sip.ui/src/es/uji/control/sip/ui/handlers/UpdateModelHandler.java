package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import es.uji.control.domain.service.connectionfactory.IControlConnectionFactory;
import es.uji.control.sip.ui.Activator;

public class UpdateModelHandler {

	private boolean canActivate = false;

	@Inject
	public UpdateModelHandler() {
		if (Activator.controlConnectionFactoryServiceTracker.getConnectionFactory() != null) {
			canActivate = true;
		}
	}
	
	@Execute
	public void execute() {
		
	}

	@CanExecute
	public boolean canExecute() {
		return canActivate;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.canActivate = true;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.canActivate = false;
	}

}
