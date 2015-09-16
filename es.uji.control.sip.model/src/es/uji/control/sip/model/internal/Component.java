package es.uji.control.sip.model.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.uji.control.controller.mifare.MifareTagId;
import es.uji.control.sip.model.IModelContainer;
import es.uji.control.sip.model.IModelListener;
import es.uji.control.sip.model.emf.sip.Cards;
import es.uji.control.sip.model.emf.sip.Model;
import es.uji.control.sip.model.emf.sip.Persons;

public class Component implements IModelContainer {
	
	private Model model;
	private List<IModelListener> listeners;
	private Object lock;
	
	public void startup() {
		model = null;
		lock = new Object();
		listeners = new ArrayList<IModelListener>(2);
	}
	
	public void shutdown() {
		removeAllListeners();
	}
	
	/////////////////////////////////////////////////////////////
	// Gestion del modelo
	/////////////////////////////////////////////////////////////
	
	@Override
	public void setCurrentModel(Model model) {
		synchronized (lock) {
			this.model = model;
			fireModelUpdatedEvent();
		}
	}

	@Override
	public Model getCurrentModel() {
		synchronized (lock) {
			return this.model;
		}
	}

	/////////////////////////////////////////////////////////////
	// API de acceso al modelo
	/////////////////////////////////////////////////////////////

	@Override
	public Persons getUserByTagId(MifareTagId tagId) {
		List<Persons> personsList = model.getModelPersonsList();
		long serialNumber = tagId.getCompactSerialNumber();
		Iterator<Persons> personsIterator = personsList.iterator();
		Cards card = null;
		
		if (getCardBySN(serialNumber) != null) {
			card = getCardBySN(serialNumber);
		} else {
			return null;
		}
		
		while (personsIterator.hasNext()) {
			Persons person = personsIterator.next();
			if(card.getPerId().equals(person.getId())) {
				return person;
			}
		}
		return null;
	}

	@Override
	public Persons getUserByTagId(long tagId) {
		List<Persons> personsList = model.getModelPersonsList();
		Iterator<Persons> personsIterator = personsList.iterator();
		Cards card = null;
		
		if (getCardByID(tagId) != null) {
			card = getCardBySN(tagId);
		} else {
			return null;
		}
		
		while (personsIterator.hasNext()) {
			Persons person = personsIterator.next();
			if(card.getPerId().equals(person.getId())) {
				return person;
			}
		}
		return null;
	}

	// Se encargara de retornar una Tarjeta si esta incluida en el modelo 
	private Cards getCardByID(long tagId) {
		List<Cards> cardsList = model.getModelCardsList();
		Iterator<Cards> cardsIterator = cardsList.iterator();

		while (cardsIterator.hasNext()) {
			Cards card = cardsIterator.next();
			if (card.getId() == tagId && card.getCancelationDate() == null) {
				return card;
			}
		}
		return null;
	}

	// Se encargara de retornar una Tarjeta si esta incluida en el modelo 
	private Cards getCardBySN(long serialNumber) {
		List<Cards> cardsList = model.getModelCardsList();
		Iterator<Cards> cardsIterator = cardsList.iterator();

		while (cardsIterator.hasNext()) {
			Cards card = cardsIterator.next();
			if (card.getSerialNumber() == serialNumber && card.getCancelationDate() == null) {
				return card;
			}
		}
		return null;
	}
	
	/////////////////////////////////////////////////////////////
	// Notificaciones del modelo
	/////////////////////////////////////////////////////////////
	
	private void fireModelUpdatedEvent() {
		for (IModelListener listener : listeners) {
			fireModelUpdatedEvent(listener);
		}
	}
	
	private void fireModelUpdatedEvent(IModelListener listener) {
		listener.modelUpdate(this);
	}
	
	@Override
	public void addListener(final IModelListener listener) {
		synchronized (lock) {
			listeners.add(listener);
		}
	}	

	@Override
	public void removeListener(IModelListener listener) {
		synchronized (lock) {
			listeners.remove(listener);
		}
	}
	
	private void removeAllListeners() {
		synchronized (lock) {
			listeners.clear();
		}
	}
	
}
