package es.uji.control.sip.ui.handlers;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.*;

public class ReadPreferenceHandler {

	private String user = null;
	private String password = null;
	
	@Execute
	public void execute(@Preference(nodePath = "oracle") IEclipsePreferences prefs) {
		
		user = prefs.get("user", "");
		password = prefs.get("password", "");
		
	    System.out.println("Usuario : " + user + " Pasword : " + password);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
