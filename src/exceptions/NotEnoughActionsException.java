package exceptions;

import views.Scene3;

public class NotEnoughActionsException extends GameActionException {

	public NotEnoughActionsException() {
		super();
	}

	public NotEnoughActionsException(String message) {
		super(message);
		Scene3.setAlertBoxContainer(message);
	}

	public NotEnoughActionsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}