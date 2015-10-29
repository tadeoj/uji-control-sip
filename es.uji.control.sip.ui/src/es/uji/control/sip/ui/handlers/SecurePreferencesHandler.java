/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
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
