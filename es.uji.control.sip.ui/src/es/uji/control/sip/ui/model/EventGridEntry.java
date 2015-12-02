package es.uji.control.sip.ui.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import es.uji.control.model.sip.ModelLogEntry;
import es.uji.control.model.sip.ModelLogSource;
import es.uji.control.model.sip.ModelLogType;

public class EventGridEntry {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	private String instant;
	private ModelLogSource source;
	private ModelLogType type;
	private String msg;
	
	public EventGridEntry(Instant instant, ModelLogSource source, ModelLogType type, String msg) {
		super();
		this.instant = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formatter);
		this.source = source;
		this.type = type;
		this.msg = msg;
	}
	
	public EventGridEntry(ModelLogEntry modelLogEntry) {
		super();
		this.instant = LocalDateTime.ofInstant(modelLogEntry.getInstant(), ZoneId.systemDefault()).format(formatter);
		this.source = modelLogEntry.getSource();
		this.type = modelLogEntry.getType();
		this.msg = modelLogEntry.getMsg();
	}
	
	public String getInstant() {
		return instant;
	}
	
	public void setInstant(String instant) {
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
