package es.uji.control.sip.ui.services;

public enum ServiceEventsEnum {

	ADDED_CONNECTION_FACTORY_SERVICE("ADDED_CONNECTION_FACTORY_SERVICE"),
	REMOVED_CONNECTION_FACTORY_SERVICE("REMOVED_CONNECTION_FACTORY_SERVICE");
	
    private final String text;

    private ServiceEventsEnum(String text) {
    	this.text = text;
	}
    @Override
    public String toString() {
        return text;
    }
	
}
