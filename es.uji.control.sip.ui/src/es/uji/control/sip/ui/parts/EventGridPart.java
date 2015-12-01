package es.uji.control.sip.ui.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import es.uji.control.model.sip.IModel;
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
	public void createComposite(Composite parent) {
		manager = new EventGridManager(new ArrayList<>(1));
		parent.setLayout(new GridLayout(1, false));
	
		Composite grid = createGrid(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(grid);

		createManagerUpdater();
	}

	private Composite createGrid(Composite parent) {
		Composite users = new Composite(parent, SWT.NONE);

		viewer = new TableViewer(users, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		createColumns();

		table = viewer.getTable();
		table.setLinesVisible(false);
		table.setHeaderVisible(true);
		
	    viewer.setContentProvider(new ArrayContentProvider());
	    viewer.setInput(manager.getCollection());

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(table);

		return users;
	}

	private void createColumns() {
		String[] titles = { "Instant", "Source", "Type", "Message" };
		int[] bounds = { 100, 100, 100, 500 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EventGridEntry e = (EventGridEntry) element;
				return e.getInstant().toString();
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
		manager.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				sync.asyncExec(() -> viewer.setInput(manager.getCollection()));
			}
		});
		
		if (modelSIP != null) {
			modelSIP.setLogger((e)-> manager.addEventGridEntry(new EventGridEntry(e)));
		}
		
	}
	
	@Inject
	@Optional
	private void subscribeModelSIPAdded(@UIEventTopic("ADDED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = modelSIP;
		}
	}

	@Inject
	@Optional
	private void subscribeModelSIPRemoved(@UIEventTopic("REMOVED_MODEL_SIP_SERVICE") IModel modelSIP) {
		synchronized (this) {
			this.modelSIP = null;
		}
	}

}
