package es.uji.control.sip.ui.handlers;

import java.time.LocalDateTime;
import java.util.List;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.domain.people.IPerson;
import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.service.connectionfactory.ControlNotImplementedException;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnection;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnectionFactory;
import es.uji.control.domain.provider.subsystem.people.IPersonStream;
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

		Job job = new Job("Carga del modelo") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IControlConnection connection = connectionFactory.createConnection();
					
					System.out.println("-------------------------------------------------------");
					System.out.println(String.format("Empieza la carga del modelo: %s", LocalDateTime.now().toString()));
					System.out.println("-------------------------------------------------------");
					
					connection.getPersonService().getAllPersons(new IPersonStream() {
					int numPersons = 0;
						
						@Override
						public void onNext(List<IPerson> persons) {
							int size = persons.size();
							System.out.println("-------------------------------------------------------");
							System.out.println(size);
							System.out.println(String.format("DNI:%s, Nombre:%s, Apellidos:%s %s ",
									persons.get(size-1).getIdentification(),
									persons.get(size-1).getName(),
									persons.get(size-1).getFirstLastName(),
									persons.get(size-1).getSecondLastName()));
							System.out.println("-------------------------------------------------------");
							numPersons = numPersons + size;
						}

						@Override
						public void onError(Throwable t) {
							System.out.println("----------------------ERROR----------------------------");
						}

						@Override
						public void onCompleted() {
							System.out.println("-------------------------------------------------------");
							System.out.println(String.format("Finaliza la carga del modelo: %s", LocalDateTime.now().toString()));
							System.out.println(String.format("Actualmente se han cargado %d personas", numPersons));
							System.out.println("-------------------------------------------------------");
						}
					});

				} catch (ControlConnectionException e) {
					MessageDialog.openError(shell, "Error", e.getMessage());
					return Status.CANCEL_STATUS;
				} catch (ControlNotImplementedException e) {
					MessageDialog.openError(shell, "Error", e.getMessage());
					return Status.CANCEL_STATUS;
				}

				return Status.OK_STATUS;
			}

		};
		job.schedule();

	}

	@CanExecute
	public boolean canExecute() {
		return canActivate;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryAdded(
			@UIEventTopic("ADDED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		this.canActivate = true;
	}

	@Inject
	@Optional
	private void subscribeConnectionFactoryRemoved(
			@UIEventTopic("REMOVED_CONNECTION_FACTORY_SERVICE") IControlConnectionFactory connectionFactory) {
		this.canActivate = false;
		this.connectionFactory = null;
	}

}
