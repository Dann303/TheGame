package exceptions;

import views.Scene3;

public class InvalidTargetException extends GameActionException{

	public InvalidTargetException() {
		super();
	}

	public InvalidTargetException(String message) {
		super(message);
		Scene3.setAlertBoxContainer(message);

	}

	public InvalidTargetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}