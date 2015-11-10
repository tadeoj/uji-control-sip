package es.uji.control.sip.ui.services;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import es.uji.control.domain.provider.service.connectionfactory.IControlConnectionFactory;

public class ControlConnectionFactoryServiceTracker extends ServiceTracker<IControlConnectionFactory, Object> {
		
	private BundleContext context;
	private IControlConnectionFactory connectionFactory;
	private IEventBroker eventBroker;

	public ControlConnectionFactoryServiceTracker(BundleContext context) {
		super(context, IControlConnectionFactory.class, null);
		this.context = context;
	}
	
	@Override
	synchronized public Object addingService(ServiceReference<IControlConnectionFactory> reference) {
		if (connectionFactory == null) {
			connectionFactory = (IControlConnectionFactory) context.getService(reference);
			eventBroker.send(ServiceEventsEnum.ADDED_CONNECTION_FACTORY_SERVICE.toString(), connectionFactory);
			return connectionFactory;
		} else {
			return null;
		}
	}

	@Override
	synchronized public void removedService(ServiceReference<IControlConnectionFactory> reference, Object service) {
		connectionFactory = null;
		eventBroker.send(ServiceEventsEnum.REMOVED_CONNECTION_FACTORY_SERVICE.toString(), connectionFactory);
	}
	
	public IControlConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public void activateEventBroker(IEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

}
