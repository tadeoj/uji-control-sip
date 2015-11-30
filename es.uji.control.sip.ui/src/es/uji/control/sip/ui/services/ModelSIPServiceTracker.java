package es.uji.control.sip.ui.services;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import es.uji.control.model.sip.IModel;

public class ModelSIPServiceTracker extends ServiceTracker<IModel, Object> {
	
	private BundleContext context;
	private IModel modelSIP;
	private IEventBroker eventBroker;
	
	public ModelSIPServiceTracker(BundleContext context) {
		super(context, IModel.class, null);
		this.context = context;
	}
	
	@Override
	synchronized public Object addingService(ServiceReference<IModel> reference) {
		if (modelSIP == null) {
			modelSIP = (IModel) context.getService(reference);
			modelSIP.setLogger((t)->System.out.println(t.toString()));
			eventBroker.send(ServiceEventsEnum.ADDED_MODEL_SIP_SERVICE.toString(), modelSIP);
			return modelSIP;
		} else {
			return null;
		}
	}

	@Override
	synchronized public void removedService(ServiceReference<IModel> reference, Object service) {
		modelSIP = null;
		eventBroker.send(ServiceEventsEnum.REMOVED_MODEL_SIP_SERVICE.toString(), modelSIP);
	}
	
	public IModel getModelSIP() {
		return modelSIP;
	}
	
	public void activateEventBroker(IEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

}
