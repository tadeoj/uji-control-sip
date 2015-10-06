package es.uji.control.sip.ui.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import es.uji.control.sip.ui.dialogs.PreferencesDialog;

public class PreferencesHandler {

	@Execute
	public void execute(IEclipseContext context) {
		PreferencesDialog dialog = ContextInjectionFactory.make(PreferencesDialog.class, context);
		dialog.open();
	}
	
}
