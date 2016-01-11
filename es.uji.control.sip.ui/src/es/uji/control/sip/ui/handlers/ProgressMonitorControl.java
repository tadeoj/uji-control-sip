package es.uji.control.sip.ui.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import es.uji.control.model.sip.IModel;

public class ProgressMonitorControl {

	private final UISynchronize sync;

	private final IModel model;

	private ProgressBar progressBar;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private GobalProgressMonitor monitor;

	@Inject
	public ProgressMonitorControl(UISynchronize sync, IModel model) {
		this.sync = Objects.requireNonNull(sync);
		this.model = model;
	}

	@PostConstruct
	public void createControls(Composite parent) {

		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setSize(SWT.DEFAULT, SWT.DEFAULT);

		Label label = new Label(comp, SWT.NONE);
		GridDataFactory.fillDefaults().hint(400, SWT.DEFAULT).applyTo(label);

		progressBar = new ProgressBar(comp, SWT.SMOOTH);
		progressBar.setBounds(100, 10, 200, 20);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(progressBar);

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

		monitor = new GobalProgressMonitor();

		Job.getJobManager().setProgressProvider(new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return monitor.addJob(job);
			}
		});
	}

	private final class GobalProgressMonitor extends NullProgressMonitor {

		private long runningTasks = 0L;

		@Override
		public void beginTask(final String name, final int totalWork) {
			sync.asyncExec(new Runnable() {

				@Override
				public void run() {
					if (runningTasks <= 0) {
						progressBar.setSelection(0);
						progressBar.setMaximum(totalWork);

					} else {
						progressBar.setMaximum(progressBar.getMaximum() + totalWork);
					}

					runningTasks++;
					progressBar.setToolTipText("Currently running: " + runningTasks + "\nLast task: " + name);
				}
			});
		}

		@Override
		public void worked(final int work) {
			sync.asyncExec(new Runnable() {

				@Override
				public void run() {
					progressBar.setSelection(progressBar.getSelection() + work);
				}
			});
		}

		public IProgressMonitor addJob(Job job) {
			if (job != null) {
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						sync.asyncExec(new Runnable() {

							@Override
							public void run() {
								runningTasks--;
								if (runningTasks > 0) {
									progressBar.setToolTipText("Currently running: " + runningTasks);
								} else {
									progressBar.setToolTipText("No background progress running.");
								}
							}
						});

						event.getJob().removeJobChangeListener(this);
					}
				});
			}
			return this;
		}
	}

}