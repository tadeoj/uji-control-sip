package es.uji.control.sip.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
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

import es.uji.control.domain.people.ILinkage;
import es.uji.control.domain.people.IPerson;
import es.uji.control.sip.ui.model.UserLinkManager;

public class LinkagesPart {
	
	static public final String ID = "es.uji.control.sip.ui.partStack.part.linkage.id";

	private UserLinkManager manager;
	
	private Table table;
	private TableViewer viewer;
	
	@PostConstruct
	public void createComposite(Composite parent, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) IPerson person) {
		this.manager = new UserLinkManager();
	
		parent.setLayout(new GridLayout(1, false));
		
		Composite links = createLinks(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(links);
		
		updatePerson(person);
	}	
	
	private Composite createLinks(Composite parent) {

		Composite usersGrid = new Composite(parent, SWT.NONE);
		usersGrid.setLayout(new GridLayout(1, false));

		viewer = new TableViewer(usersGrid, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		createColumns();

		table = viewer.getTable();
		table.setLinesVisible(false);
		table.setHeaderVisible(true);
		table.setVisible(true);

		viewer.setContentProvider(new ObservableListContentProvider());
		viewer.setInput(manager.getCollection());

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(table);

		return usersGrid;
	}

	private void createColumns() {
		String[] titles = { "Tipo" };
		int[] bounds = { 190 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ILinkage e = (ILinkage) element;
				return e.getName();
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
	
	private void updatePerson(IPerson person) {
		manager.clean();
		if (person != null && person.getLinkages() != null)
		manager.setUserLinksList(person.getLinkages());
	}
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("SELECT_PERSON") IPerson person) {
		updatePerson(person);
	}
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("CLEAN_PERSON") boolean clean) {
		manager.clean();
	}
	
}
