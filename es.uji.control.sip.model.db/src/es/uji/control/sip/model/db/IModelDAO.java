package es.uji.control.sip.model.db;

import es.uji.control.sip.model.emf.sip.Model;

public interface IModelDAO {
	
	// Crea todos los entityManagers necesarios en el DAO
	public void createEntitiesManager() throws QueryModelException;
	
	// Consulta en la base de datos el modelo.
	public Model queryModel() throws QueryModelException;
	
	// Sincroniza las fotos del modelo persistente en disco
	public void syncronizePhotos() throws QueryModelException;
	
}
