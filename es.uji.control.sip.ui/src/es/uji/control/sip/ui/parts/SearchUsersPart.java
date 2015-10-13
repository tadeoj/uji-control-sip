package es.uji.control.sip.ui.parts;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class SearchUsersPart {
	
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
	
	private Table listUsers;
	
	@Inject
	private ESelectionService selectionService;
	
	@Inject
	public SearchUsersPart(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Composite banner = createBanner(parent);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(banner);
		
		Composite users = createUsers(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(users);
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
		GridDataFactory.fillDefaults().grab(true, false).indent(10, 0).align(SWT.BEGINNING, SWT.CENTER).applyTo(labelDni);
		
		textDni = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textDni);
		
		labelFirstLastName = new Label(banner, SWT.NONE);
		labelFirstLastName.setText("Primer Apellido");
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.BEGINNING, SWT.CENTER).applyTo(labelFirstLastName);
		
		textFirstLastName = new Text(banner, SWT.FILL | SWT.WRAP | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textFirstLastName);
		
		labelSecondLastName = new Label(banner, SWT.NONE);
		labelSecondLastName.setText("Segundo Apellido");
		GridDataFactory.fillDefaults().grab(true, false).indent(10, 0).align(SWT.BEGINNING, SWT.CENTER).applyTo(labelSecondLastName);
		
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
		
		Composite users = new Composite(parent, SWT.NONE);
		
		listUsers = new Table(users, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		listUsers.setLinesVisible(false);
		listUsers.setHeaderVisible(true);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(listUsers);

		TableColumn columnNombre = new TableColumn(listUsers, SWT.LEFT);
		TableColumn columnPrimerapellido = new TableColumn(listUsers, SWT.LEFT);
		TableColumn columnSegundoApellido = new TableColumn(listUsers, SWT.LEFT);
		TableColumn columnDni = new TableColumn(listUsers, SWT.LEFT);
		
		TableColumnLayout layout = new TableColumnLayout();
		layout.setColumnData(columnNombre, new ColumnWeightData(200));
		layout.setColumnData(columnPrimerapellido, new ColumnWeightData(200));
		layout.setColumnData(columnSegundoApellido, new ColumnWeightData(200));
		layout.setColumnData(columnDni, new ColumnWeightData(200));
		users.setLayout(layout);
		
		return users;
	}

	@Focus
	void setFocus() {
		textName.setFocus();
	}
	
}
