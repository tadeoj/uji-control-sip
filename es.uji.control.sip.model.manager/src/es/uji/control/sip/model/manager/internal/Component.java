package es.uji.control.sip.model.manager.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uji.control.sip.model.IModelContainer;
import es.uji.control.sip.model.cache.ICache;
import es.uji.control.sip.model.db.IModelDAO;
import es.uji.control.sip.model.manager.IUpdateModel;
import es.uji.control.sip.model.manager.UpdateState;
import es.uji.control.sip.model.manager.UpdateStateEnum;

public class Component implements IUpdateModel {

	static final Logger logger = LoggerFactory.getLogger(Component.class);
	
	private IModelContainer modelContainer;
	private ICache cache;
	private IModelDAO modelDAO;
	
	public void startup() {
		System.out.println("");
	}
	
	public void shutdown() {
		System.out.println("");
	}
	
	public void setModel(IModelContainer model) {
		this.modelContainer = model;
	}
	
	public void setCache(ICache cache) {
		this.cache = cache;
	}
	
	public void setModelDAO(IModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}
	
	//////////////////////////////////////////////////////////////////
	// Metodos encargados de la actualización del modelo.
	//////////////////////////////////////////////////////////////////
	
	@Override
	public UpdateState firstUpdate() {
		// Como acaba de iniciarse el sistema y no hay datos en el modelo
		// lo cargamos con lo que encontramos en la cache.
		modelContainer.setCurrentModel(cache.readModelFromCache());
		// Como ya proveemos de un modelo inicial, ya podemos intentar actualiza
		// los datos desde el servidor.
		return update();
	}

	@Override
	public UpdateState update() {
		// Se actualiza la cache y el modelo desde el servidor.
		try {
			cache.writeModelToCache(modelDAO.queryModel());
			modelContainer.setCurrentModel(cache.readModelFromCache());
			return new UpdateState(UpdateStateEnum.OK, null);
		} catch (Exception e) {
			return new UpdateState(UpdateStateEnum.UPDATE_ERROR, e);
		}
	}
}
