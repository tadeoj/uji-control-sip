package es.uji.control.sip.ui.parts;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import es.uji.control.model.sip.IModel;
import es.uji.control.model.sip.ModelLogEntry;
import es.uji.control.sip.ui.model.EventGridEntry;
import es.uji.control.sip.ui.model.EventGridManager;

public class EventGridPart {

	private TableViewer viewer;
	private Table table;
	private EventGridManager manager;
	private IModel modelSIP;

	@Inject
	private UISynchronize sync;

	@PostConstruct
	public void createComposite(Composite parent, IModel modelSIP) {
		manager = new EventGridManager();
		parent.setLayout(new GridLayout(1, false));

		Composite grid = createGrid(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(grid);

		this.modelSIP = modelSIP;

		createManagerUpdater();
	}

	private Composite createGrid(Composite parent) {
		Composite users = new Composite(parent, SWT.NONE);
		users.setLayout(new GridLayout(1, false));

		viewer = new TableViewer(users, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		createColumns();

		table = viewer.getTable();
		table.setLinesVisible(false);
		table.setHeaderVisible(true);
		table.setVisible(true);

		viewer.setContentProvider(new ObservableListContentProvider());
		viewer.setInput(manager.getCollection());

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(table);

		return users;
	}

	private void createColumns() {
		String[] titles = { "Instant", "Source", "Type", "Message" };
		int[] bounds = { 180, 100, 100, 370 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventGridEntry e = (EventGridEntry) element;
				return e.getInstant();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventGridEntry e = (EventGridEntry) element;
				return e.getSource().toString();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventGridEntry e = (EventGridEntry) element;
				return e.getType().name();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventGridEntry e = (EventGridEntry) element;
				return e.getMsg();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void createManagerUpdater() {

		if (modelSIP != null) {
			modelSIP.setLogger(new Consumer<ModelLogEntry>() {

				@Override
				public void accept(ModelLogEntry t) {
					sync.asyncExec(new Runnable() {

						@Override
						public void run() {
							manager.addEventGridEntry(new EventGridEntry(t));
							table.setSelection(manager.getLastElementPosition());
						}
					});
				}
			});

		}

	}

}
