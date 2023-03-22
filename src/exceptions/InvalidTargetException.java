package exceptions;

public class InvalidTargetException extends GameActionException{

	public InvalidTargetException() {
		super();
	}

	public InvalidTargetException(String message) {
		super(message);
	}

	public InvalidTargetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
