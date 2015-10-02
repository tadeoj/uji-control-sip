package es.uji.control.sip.ui.handlers;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.*;
import org.osgi.service.prefs.BackingStoreException;

public class WritePreferenceHandler {

	@Execute
	public void execute(@Preference(nodePath = "oracle") IEclipsePreferences prefs) {
		
		prefs.put("user", "tadeo");
		prefs.put("password", "prueba");
		
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
	    System.out.println((this.getClass().getSimpleName() + " ha generado unas preferencias"));
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
