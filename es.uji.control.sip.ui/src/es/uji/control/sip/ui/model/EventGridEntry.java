package es.uji.control.sip.ui.model;

import java.time.Instant;

import es.uji.control.model.sip.ModelLogEntry;
import es.uji.control.model.sip.ModelLogSource;
import es.uji.control.model.sip.ModelLogType;

public class EventGridEntry {

	private Instant instant;
	private ModelLogSource source;
	private ModelLogType type;
	private String msg;
	
	public EventGridEntry(Instant instant, ModelLogSource source, ModelLogType type, String msg) {
		super();
		this.instant = instant;
		this.source = source;
		this.type = type;
		this.msg = msg;
	}
	
	public EventGridEntry(ModelLogEntry modelLogEntry) {
		super();
		this.instant = modelLogEntry.getInstant();
		this.source = modelLogEntry.getSource();
		this.type = modelLogEntry.getType();
		this.msg = modelLogEntry.getMsg();
	}
	
	public Instant getInstant() {
		return instant;
	}
	
	public void setInstant(Instant instant) {
		this.instant = instant;
	}
	
	public ModelLogSource getSource() {
		return source;
	}
	
	public void setSource(ModelLogSource source) {
		this.source = source;
	}
	
	public ModelLogType getType() {
		return type;
	}
	
	public void setType(ModelLogType type) {
		this.type = type;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
