package exceptions;

import gui.mrd.Scene3;

public class NoAvailableResourcesException extends GameActionException {

	public NoAvailableResourcesException() {
		super();
	}

	public NoAvailableResourcesException(String message) {
		super(message);
		Scene3.setAlertBoxContainer(message);
	}

	public NoAvailableResourcesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}