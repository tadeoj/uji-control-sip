package es.uji.control.sip.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import es.uji.control.domain.people.ILinkage;
import es.uji.control.domain.people.IPerson;
import es.uji.control.sip.ui.model.UserLinkManager;

public class UserPart {

	static public final String ID = "es.uji.control.sip.ui.partStack.part.user.id";
	
	private UserLinkManager manager;
	
	private Label labelName;
	private Label labelDni;
	private Label labelFirstLastName;
	private Label labelSecondLastName;
	private Label textName;
	private Label textDni;
	private Label textFirstLastName;
	private Label textSecondLastName;

	private Table table;
	private Label foto;
	private TableViewer viewer;

	@PostConstruct
	public void createComposite(Composite parent) {
		this.manager = new UserLinkManager();
		
		parent.setLayout(new GridLayout(1, false));

		Composite banner = createBanner(parent);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(banner);

		Composite links = createLinks(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(links);
	}

	@Focus
	public void setFocus() {
	}

	private Composite createBanner(Composite parent) {

		Composite banner = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(5, true);
		banner.setLayout(layout);
		
		foto = new Label(banner, SWT.NONE); 
		foto.setText("");
		foto.setSize(100, 100);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).span(1, 3).applyTo(foto);

		labelName = new Label(banner, SWT.NONE);
		labelName.setText("Nombre");
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).indent(5, 5).applyTo(labelName);

		textName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(false, false).indent(10, 5).applyTo(textName);

		labelDni = new Label(banner, SWT.NONE);
		labelDni.setText("DNI");
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).indent(15, 5).applyTo(labelDni);

		textDni = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(false, false).indent(10, 5).applyTo(textDni);

		labelFirstLastName = new Label(banner, SWT.NONE);
		labelFirstLastName.setText("Primer apellido");
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).indent(5, 10).applyTo(labelFirstLastName);

		textFirstLastName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(false, false).indent(10, 10).applyTo(textFirstLastName);

		labelSecondLastName = new Label(banner, SWT.NONE);
		labelSecondLastName.setText("Segundo apellido");
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).indent(15, 10).applyTo(labelSecondLastName);

		textSecondLastName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(false, false).indent(10, 10).applyTo(textSecondLastName);

		return banner;
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
		String[] titles = { "Vinculos" };
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
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("SELECT_PERSON") IPerson person) {
		textName.setText(person.getName());
		textFirstLastName.setText(person.getFirstLastName());
		textSecondLastName.setText(person.getSecondLastName());
		textDni.setText(person.getIdentification());
		manager.clean();
		manager.setUserLinksList(person.getLinkages());
	}
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("CLEAN_PERSON") boolean clean) {
		textName.setText("");
		textFirstLastName.setText("");
		textSecondLastName.setText("");
		textDni.setText("");
		manager.clean();
	}


}
