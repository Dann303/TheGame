package exceptions;

import gui.mrd.Scene3;

public class MovementException extends GameActionException{

	public MovementException() {
		super();
	}

	public MovementException(String message) {
		super(message);
		Scene3.setAlertBoxContainer(message);
	}

	public MovementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}