package es.uji.control.sip.model;

import es.uji.control.controller.mifare.MifareTagId;
import es.uji.control.sip.model.emf.sip.Model;
import es.uji.control.sip.model.emf.sip.Persons;

public interface IModelContainer {
	
	public Model getCurrentModel();
	public void setCurrentModel(Model model);
	
	public Persons getUserByTagId(MifareTagId mifareTagId);
	public Persons getUserByTagId(long tagId);
		
	public void addListener(IModelListener listener);
	public void removeListener(IModelListener listener);

}
