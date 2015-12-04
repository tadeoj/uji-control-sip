package es.uji.control.sip.ui.parts;

public enum PartsEnum {

	SELECT_PERSON("SELECT_PERSON"),
	CLEAN_PERSON("CLEAN_PERSON");

	private final String text;

	private PartsEnum(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
