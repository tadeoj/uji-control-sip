package es.uji.control.sip.model.cache;

import es.uji.control.sip.model.emf.sip.Model;

public interface ICache {
	
	// Metodos sobre la cache.
	public Model readModelFromCache(long zoneId);
	public void writeModelToCache(Model model);

}
