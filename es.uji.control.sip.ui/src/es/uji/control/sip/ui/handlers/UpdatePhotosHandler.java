package es.uji.control.sip.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import es.uji.control.model.sip.IModel;

public class UpdatePhotosHandler {
	
	private IEventBroker eventBroker; 
	private IModel model;
	private boolean updating = true;
	
	@Inject
	public UpdatePhotosHandler(IEventBroker eventBroker, IModel model) {
		this.eventBroker = eventBroker;
		this.model = model;
		
	}

}
