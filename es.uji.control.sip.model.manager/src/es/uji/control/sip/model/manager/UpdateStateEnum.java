package es.uji.control.sip.model.manager;

public enum UpdateStateEnum {
	
	OK (0,"La actualización se ha realizado correctamente."),
	UPDATE_ERROR (1, "No se ha podido actualizar el modelo.");
	
	private int id;
	private String description;
	
	private UpdateStateEnum(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return super.toString();
	}
	
	public String getDescription() {
		return description;
	}
	
	public static UpdateStateEnum getById(int id) {
		for (UpdateStateEnum updateState : UpdateStateEnum.values()) {
			if (id == updateState.getId())
				return updateState;
		}
		return null;
	}
}
