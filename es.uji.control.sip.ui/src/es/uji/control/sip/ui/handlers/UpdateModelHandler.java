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
import es.uji.control.model.sip.IModelSIP;
import es.uji.control.sip.ui.Activator;

public class UpdateModelHandler {

	private IControlConnectionFactory connectionFactory;
	private IModelSIP modelSIP;
	private Shell shell;

	@Inject
	public UpdateModelHandler(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		if (Activator.controlConnectionFactoryServiceTracker.getConnectionFactory() != null && Activator.modelSIPServiceTracker.getModelSIP() != null) {
			connectionFactory = Activator.controlConnectionFactoryServiceTracker.getConnectionFactory();
			modelSIP = Activator.modelSIPServiceTracker.getModelSIP();
			canActivate();
			this.shell = shell;
		}
	}

	@Execute
	public void execute() {

		Job job = new Job("Carga del modelo") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				modelSIP.updateModelFromBackend(t -> System.out.println(t.toString()));
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		// MessageDialog.openError(shell, "Error", e.getMessage());

	}

	@CanExecute
	public boolean canExecute() {
		return canActivate();
	}

	private boolean canActivate() {
		if (modelSIP != null && connectionFactory != null) {
			return true;
		} else {
			return false;
		}
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		canActivate();
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.connectionFactory = null;
		canActivate();
	}

	@Inject
	@Optional
	private void subscribeModelSIPAdded(@UIEventTopic("ADDED_MODEL_SIP_SERVICE") IModelSIP modelSIP) {
		this.modelSIP = modelSIP;
		canActivate();
	}

	@Inject
	@Optional
	private void subscribeModelSIPRemoved(@UIEventTopic("REMOVED_MODEL_SIP_SERVICE") IModelSIP modelSIP) {
		this.modelSIP = null;
		canActivate();
	}

}
