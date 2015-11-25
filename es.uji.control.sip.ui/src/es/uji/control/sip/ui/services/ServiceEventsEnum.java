package es.uji.control.sip.ui.services;

public enum ServiceEventsEnum {

	ADDED_CONNECTION_FACTORY_SERVICE("ADDED_CONNECTION_FACTORY_SERVICE"),
	REMOVED_CONNECTION_FACTORY_SERVICE("REMOVED_CONNECTION_FACTORY_SERVICE"),
	ADDED_MODEL_SIP_SERVICE("ADDED_MODEL_SIP_SERVICE"),
	REMOVED_MODEL_SIP_SERVICE("REMOVED_MODEL_SIP_SERVICE");
	
    private final String text;

    private ServiceEventsEnum(String text) {
    	this.text = text;
	}
    @Override
    public String toString() {
        return text;
    }
	
}
