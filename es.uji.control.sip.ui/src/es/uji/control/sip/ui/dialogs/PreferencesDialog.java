package es.uji.control.sip.ui.dialogs;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;

@SuppressWarnings("restriction")
public class PreferencesDialog extends TitleAreaDialog {
	
	@Inject
	@Preference(nodePath = "jdbc", value = "username") 
	private String username;
	
	@Inject
	@Preference(nodePath = "jdbc", value = "password") 
	private String password;
	
	@Inject
	@Preference(nodePath = "jdbc", value = "host") 
	private String host;
	
	private IEclipsePreferences prefs;
	
	private Text usernameField;
	private Text passwordField;
	private Text hostField;

	@Inject
	public PreferencesDialog(@Named(IServiceConstants.ACTIVE_SHELL) Shell parentShell, @Preference(nodePath = "jdbc") IEclipsePreferences prefs) {
		super(parentShell);
		this.prefs = prefs;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		getShell().setText("Conexión JDBC");
		setTitle("Parametros de conexión JDBC");
		setMessage("Configure los parametros de conexión JDBC");
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout(2, false));
		Label l = new Label(container, SWT.NONE);
		l.setText("Username");
		usernameField = new Text(container, SWT.BORDER);
		usernameField.setText(username == null ? "" : username);
		usernameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		l = new Label(container, SWT.NONE);
		l.setText("Password");
		passwordField = new Text(container, SWT.BORDER);
		passwordField.setText(password == null ? "" : password);
		passwordField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		l = new Label(container, SWT.NONE);
		l.setText("Host");
		hostField = new Text(container, SWT.BORDER);
		hostField.setText(host == null ? "" : host);
		hostField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return area;
	}

	@Override
	protected void okPressed() {	
		prefs.put("username", usernameField.getText());
		prefs.put("password", passwordField.getText());
		prefs.put("host", hostField.getText());
		try {
			prefs.flush();
			super.okPressed();
		} catch (BackingStoreException e) {
			ErrorDialog.openError(getShell(), "Error", "Error mientras se almacenaban las preferencias", new Status(IStatus.ERROR, "es.uji.control.sip.ui", e.getMessage(), e));
		}
	}
	
}