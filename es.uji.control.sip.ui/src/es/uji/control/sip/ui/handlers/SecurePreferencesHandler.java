package es.uji.control.sip.ui.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import es.uji.control.sip.ui.dialogs.SecurePreferencesDialog;

public class SecurePreferencesHandler {

	@Execute
	public void execute(IEclipseContext context) {
		SecurePreferencesDialog dialog = ContextInjectionFactory.make(SecurePreferencesDialog.class, context);
		dialog.open();
	}
	
}
