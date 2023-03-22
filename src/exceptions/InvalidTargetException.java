package exceptions;

public class InvalidTargetException extends GameActionException{

	public InvalidTargetException() {
		super();
	}

	public InvalidTargetException(String message) {
		super(message);
	}

	// w mesh fahem el constructor dah
	public InvalidTargetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
