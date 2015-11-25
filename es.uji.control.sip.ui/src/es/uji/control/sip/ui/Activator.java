/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.sip.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import es.uji.control.sip.ui.services.ControlConnectionFactoryServiceTracker;
import es.uji.control.sip.ui.services.ModelSIPServiceTracker;

public class Activator implements BundleActivator {

	private static BundleContext context;

	public static ControlConnectionFactoryServiceTracker controlConnectionFactoryServiceTracker; 
	public static ModelSIPServiceTracker modelSIPServiceTracker;

	public Activator() {
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		initTrackers(context);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		closeTrackers();
	}

	private void initTrackers(BundleContext context) {
		controlConnectionFactoryServiceTracker = new ControlConnectionFactoryServiceTracker(context);
		modelSIPServiceTracker = new ModelSIPServiceTracker(context);
	}
	
	private void closeTrackers() {
		controlConnectionFactoryServiceTracker.close();
		modelSIPServiceTracker.close();
	}

	public static BundleContext getContext() {
		return context;
	}
	
}