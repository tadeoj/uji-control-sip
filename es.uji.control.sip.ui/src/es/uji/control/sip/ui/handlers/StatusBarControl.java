package es.uji.control.sip.ui.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import es.uji.control.model.sip.IModel;

public class StatusBarControl {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	private IModel model;
	
	private ProgressBar progressBar;
	
	@Inject
	private UISynchronize sync;
	
	@PostConstruct
	public void createGui(Composite parent, IModel modelSIP) {
		
		this.model = modelSIP;
		
		final Composite comp = new Composite(parent, SWT.NONE);
	    comp.setLayout(new GridLayout());
	    Label label = new Label(comp, SWT.NONE);
	    GridDataFactory.fillDefaults().hint(600, SWT.DEFAULT).applyTo(label);
	    
	    progressBar = new ProgressBar(comp, SWT.INDETERMINATE);
	    GridDataFactory.fillDefaults().hint(SWT.DEFAULT, SWT.DEFAULT).align(SWT.RIGHT, SWT.CENTER).applyTo(progressBar);
	    
	    if (model != null) {
	    	model.setUpdateModelStateTracker(new Consumer<LocalDateTime>() {
				
				@Override
				public void accept(LocalDateTime t) {
					sync.asyncExec(new Runnable() {
						
						@Override
						public void run() {
							label.setText(String.format("Modelo cargado: %s", t.format(formatter)));
						}
					});
				}
			});
	    }
	}
	
	public void startJob(String name) {
		Job job = new Job(name) {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// TODO: Falta implementar 
				monitor.worked(0);

				if (monitor.isCanceled() == true) {
		    		monitor.done();
		    		return Status.CANCEL_STATUS;
				}
				
				
				
				return null;
			}
		};
		
		job.schedule();
	}
	
}