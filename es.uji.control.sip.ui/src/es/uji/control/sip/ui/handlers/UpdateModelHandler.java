package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.domain.provider.service.connectionfactory.IControlConnectionFactory;
import es.uji.control.model.sip.IModel;
import es.uji.control.sip.ui.Activator;

public class UpdateModelHandler {

	private IControlConnectionFactory connectionFactory;
	private IModel modelSIP;
	private Shell shell;
	private boolean isActive = false;
	
	@Inject
	public UpdateModelHandler(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		connectionFactory = Activator.controlConnectionFactoryServiceTracker.getConnectionFactory();
		modelSIP = Activator.modelSIPServiceTracker.getModelSIP();
		canActivate();
		this.shell = shell;
	}

	@Execute
	public void execute() {

		Job job = new Job("Carga del modelo") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				modelSIP.updateModelFromBackend();
				return Status.OK_STATUS;
			}
		};
		job.schedule();

	}

	@CanExecute
	public boolean canExecute() {
		return isActive;
	}

	private void canActivate() {
		if (modelSIP != null && connectionFactory != null) {
			isActive = true;
		} else {
			isActive = false;
		}
		canExecute();
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		synchronized (this) {
			this.connectionFactory = connectionFactory;
			canActivate();
		}	
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		synchronized (this) {
			this.connectionFactory = null;
			canActivate();
		}
	}

	@Inject
	@Optional
	private void subscribeModelSIPAdded(@UIEventTopic("ADDED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = modelSIP;
			canActivate();
		}
	}

	@Inject
	@Optional
	private void subscribeModelSIPRemoved(@UIEventTopic("REMOVED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = null;
			canActivate();
		}
	}

}
