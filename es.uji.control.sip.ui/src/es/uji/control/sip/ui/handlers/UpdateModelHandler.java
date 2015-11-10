package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnection;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnectionFactory;
import es.uji.control.sip.ui.Activator;

public class UpdateModelHandler {

	private boolean canActivate = false;
	private IControlConnectionFactory connectionFactory;
	private Shell shell;

	@Inject
	public UpdateModelHandler(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		if (Activator.controlConnectionFactoryServiceTracker.getConnectionFactory() != null) {
			connectionFactory = Activator.controlConnectionFactoryServiceTracker.getConnectionFactory();
			canActivate = true;
			this.shell = shell;
		}
	}
	
	@Execute
	public void execute() {
		
		IControlConnection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
		} catch (ControlConnectionException e) {
			MessageDialog.openError(shell, "Error", e.getMessage());
		}
		
	}

	@CanExecute
	public boolean canExecute() {
		return canActivate;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		this.canActivate = true;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.canActivate = false;
		this.connectionFactory = null;
	}

}
