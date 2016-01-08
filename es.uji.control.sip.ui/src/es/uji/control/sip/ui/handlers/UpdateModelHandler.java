package es.uji.control.sip.ui.handlers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import es.uji.control.model.sip.IModel;

public class UpdateModelHandler {

	private IEventBroker eventBroker;
	private IModel modelSIP;
	private boolean updating = true;
	private Job job;

	@Inject
	public UpdateModelHandler(IEventBroker eventBroker, IModel modelSIP, MApplication app, EModelService service) {
		// Inyeccion
		this.eventBroker = eventBroker;

		// Servicios OSGI
		this.modelSIP = modelSIP;

		// Inicializacion del estado de la actualizacion
		this.modelSIP.setUpdateModelUpdatingTracker(this::setUpdating);
	
		job = new Job("Actualizando modelo") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					modelSIP.updateModelFromBackend();
					monitor.beginTask("Actualizando modelo", 100);
					TimeUnit.SECONDS.sleep(1);
					monitor.worked(10);
					TimeUnit.SECONDS.sleep(1);
					monitor.worked(20);
					
					return Status.OK_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
				}
			}
			
		};
				
	}

	private void setUpdating(Boolean updating) {
		// Se actualiza el estado
		this.updating = updating;
		// Se fuerza al UI a actualizarse
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	@Execute
	public void execute() {
		job.schedule();
	}

	@CanExecute
	public boolean canExecute() {
		return !updating;
	}

}
