package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import es.uji.control.domain.service.connectionfactory.IControlConnectionFactory;

public class UpdateModelHandler {

	private boolean canActivate = false;

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
		canExecute();
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.canActivate = false;
		canExecute();

	}

}
