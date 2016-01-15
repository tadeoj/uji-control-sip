/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.sip.ui.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import es.uji.control.domain.people.IPerson;
import es.uji.control.model.sip.IModel;
import es.uji.control.sip.ui.model.PersonQuery;
import es.uji.control.sip.ui.model.SearchUsersManager;

@SuppressWarnings("restriction")
public class SearchUsersPart {

	public static final String PART_SASH = "es.uji.control.sip.ui.partsashcontainer.1";
	
	@Inject
	private Logger logger;

	@Inject
	private UISynchronize sync;
	
	@Inject 
	private ESelectionService selectionService;

	@Inject
	private IEventBroker eventBroker;
	
	private IModel modelSIP;

	private Label labelName;
	private Label labelDni;
	private Label labelFirstLastName;
	private Label labelSecondLastName;

	private Text textName;
	private Text textDni;
	private Text textFirstLastName;
	private Text textSecondLastName;

	private Button search;
	private Button clean;

	private Table table;
	private TableViewer viewer;

	private SearchUsersManager manager;
	private EnterKeyPressed enterKeypressed;

	private MPartSashContainer userPartSash;

	@PostConstruct
	public void createComposite(Composite parent, IModel modelSIP, MApplication app, EModelService modelService) {
		this.modelSIP = modelSIP;
		this.manager = new SearchUsersManager();
		this.enterKeypressed = new EnterKeyPressed();
		
		userPartSash = (MPartSashContainer) modelService.find(PART_SASH, app);
	
		parent.setLayout(new GridLayout(1, false));

		Composite banner = createBanner(parent);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(banner);

		Composite users = createUsers(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(users);

		createListeners();
		
	}

	private Composite createBanner(Composite parent) {

		Composite banner = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(4, false);
		banner.setLayout(layout);

		labelName = new Label(banner, SWT.NONE);
		labelName.setText("Nombre");
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.BEGINNING, SWT.CENTER).applyTo(labelName);

		textName = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textName);

		labelDni = new Label(banner, SWT.NONE);
		labelDni.setText("DNI");
		GridDataFactory.fillDefaults().grab(true, false).indent(10, 0).align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(labelDni);

		textDni = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textDni);

		labelFirstLastName = new Label(banner, SWT.NONE);
		labelFirstLastName.setText("Primer Apellido");
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.BEGINNING, SWT.CENTER).applyTo(labelFirstLastName);

		textFirstLastName = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textFirstLastName);

		labelSecondLastName = new Label(banner, SWT.NONE);
		labelSecondLastName.setText("Segundo Apellido");
		GridDataFactory.fillDefaults().grab(true, false).indent(10, 0).align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(labelSecondLastName);

		textSecondLastName = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textSecondLastName);

		Composite buttons = new Composite(parent, SWT.NONE);
		GridLayout buttonsLayout = new GridLayout(2, false);
		buttons.setLayout(buttonsLayout);

		clean = new Button(buttons, SWT.PUSH);
		clean.setText("Limpiar");

		search = new Button(buttons, SWT.PUSH);
		search.setText("Buscar");
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.END, SWT.CENTER).applyTo(buttons);

		return banner;
	}

	private Composite createUsers(Composite parent) {

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
		String[] titles = { "Nombre", "Primer apellido", "Segundo apellido", "DNI/Identificación" };
		int[] bounds = { 190, 190, 190, 180 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IPerson e = (IPerson) element;
				return e.getName();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IPerson e = (IPerson) element;
				return e.getFirstLastName();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IPerson e = (IPerson) element;
				return e.getSecondLastName();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IPerson e = (IPerson) element;
				return e.getIdentification();
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

	private void createListeners() {
		textName.addKeyListener(enterKeypressed);
		textFirstLastName.addKeyListener(enterKeypressed);
		textSecondLastName.addKeyListener(enterKeypressed);
		textDni.addKeyListener(enterKeypressed);
		search.addListener(SWT.Selection, (e) -> {
			if (e.type == SWT.Selection) {
				searchUser();
			}
		});

		clean.addListener(SWT.Selection, (e) -> {
			if (e.type == SWT.Selection) {
				sync.asyncExec(new Runnable() {
					@Override
					public void run() {
						manager.clean();
						textName.setText("");
						textFirstLastName.setText("");
						textSecondLastName.setText("");
						textDni.setText("");
						eventBroker.send(PartsEnum.CLEAN_PERSON.toString(), true);
						userPartSash.setVisible(false);
					}
				});
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				sync.asyncExec(new Runnable() {
					@Override
					public void run() {						
						userPartSash.setVisible(true);
						IStructuredSelection selection = (IStructuredSelection) event.getSelection();
						selectionService.setSelection(selection.getFirstElement());
			
						eventBroker.send(PartsEnum.SELECT_PERSON.toString(), (IPerson) selection.getFirstElement());
					}
				});
			}
		});

	}

	private void searchUser() {
		PersonQuery query = new PersonQuery(textName.getText(), textFirstLastName.getText(),
				textSecondLastName.getText(), textDni.getText());
		try {
			final List<IPerson> result = modelSIP.searchPerson(query);
			sync.asyncExec(new Runnable() {
				@Override
				public void run() {
					manager.clean();
					manager.setUsersList(result);
				}
			});
		} catch (Exception e1) {
			logger.error("Error al intentar buscar la persona en el emodelo EMF.");
		}
	}

	private class EnterKeyPressed implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.keyCode == SWT.CR) {
				searchUser();
			}
		}

	}
	
	@Focus
	void setFocus() {
		textName.setFocus();
	}

	@PreDestroy
	private void partDestroyed() {
	}

}
