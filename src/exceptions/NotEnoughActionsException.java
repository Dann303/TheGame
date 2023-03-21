package exceptions;

public class NotEnoughActionsException extends GameActionException {

	public NotEnoughActionsException() {
		super();
	}

	public NotEnoughActionsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotEnoughActionsException(String message) {
		super(message);
	}

}
