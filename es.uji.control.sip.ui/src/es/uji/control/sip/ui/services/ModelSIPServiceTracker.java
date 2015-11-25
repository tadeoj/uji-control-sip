package es.uji.control.sip.ui.services;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import es.uji.control.model.sip.IModelSIP;

public class ModelSIPServiceTracker extends ServiceTracker<IModelSIP, Object> {

	private BundleContext context;
	private IModelSIP modelSIP;
	private IEventBroker eventBroker;
	
	public ModelSIPServiceTracker(BundleContext context) {
		super(context, IModelSIP.class, null);
		this.context = context;
	}
	
	@Override
	synchronized public Object addingService(ServiceReference<IModelSIP> reference) {
		if (modelSIP == null) {
			modelSIP = (IModelSIP) context.getService(reference);
			eventBroker.send(ServiceEventsEnum.ADDED_MODEL_SIP_SERVICE.toString(), modelSIP);
			return modelSIP;
		} else {
			return null;
		}
	}

	@Override
	synchronized public void removedService(ServiceReference<IModelSIP> reference, Object service) {
		modelSIP = null;
		eventBroker.send(ServiceEventsEnum.REMOVED_MODEL_SIP_SERVICE.toString(), modelSIP);
	}
	
	public IModelSIP getModelSIP() {
		return modelSIP;
	}
	
	public void activateEventBroker(IEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

}
