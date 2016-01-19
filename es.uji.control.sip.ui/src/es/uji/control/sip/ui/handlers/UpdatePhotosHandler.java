package es.uji.control.sip.ui.handlers;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;

import es.uji.control.model.sip.IModel;

public class UpdatePhotosHandler {
	
	private IEventBroker eventBroker; 
	private IModel model;
	private boolean updating = true;
	private Job job;

	@Inject
	public UpdatePhotosHandler(IEventBroker eventBroker, IModel model) {
		// Inyeccion
		this.eventBroker = eventBroker;
		
		// Servicios OSGI
		this.model = model;
		
		// Inicializacion del estado de la actualizacion
		this.model.setUpdatePhotosUpdatingTracker(this::setUpdating);
		
		job = new Job("Actualizando modelo") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					model.updatePhotosFromBackend();
					monitor.beginTask("Actualizando fotos", IProgressMonitor.UNKNOWN);
					
					while (updating) {
						TimeUnit.MILLISECONDS.sleep(100);
						monitor.worked(1);
					}
					
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
		if (eventBroker != null) {
			eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
		}
	}

	@Execute
	public void execute() {
		job.setUser(true);
		job.schedule();
	}

	@CanExecute
	public boolean canExecute() {
		return !updating;
	}
	
	@PreDestroy
	public void preDestroy() {
		eventBroker = null;
		job.done(Status.CANCEL_STATUS);
	}
	
}
