package es.uji.control.sip.model.manager;

public class UpdateState {
	
	private UpdateStateEnum updateStateEnum;
	private Throwable throwable;
	
	public UpdateState(UpdateStateEnum updateStateEnum, Throwable throwable) {
		this.updateStateEnum = updateStateEnum;
		this.throwable = throwable;
	}

	public UpdateStateEnum getUpdateStateEnum() {
		return updateStateEnum;
	}

	public Throwable getThrowable() {
		return throwable;
	}
	
}
