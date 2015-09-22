package es.uji.control.sip.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator extends AbstractUIPlugin {
	
	public static final String PLUGIN_ID = "es.uji.control.sip.ui";
	
	private static Activator plugin;
	
	static final Logger logger = LoggerFactory.getLogger(Activator.class);
	
	public Activator() {	
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		initTrackers(context);
	}
	
	public void stop(BundleContext context) throws Exception {
		closeTrackers();
		plugin = null;
		super.stop(context);
	}
	
	private void initTrackers(BundleContext context) {

	}

	private void closeTrackers() {
		
	}
	
	public static Activator getDefault() {
		return plugin;
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	static public Logger getLogger() {
		return logger;
	}
	
}
