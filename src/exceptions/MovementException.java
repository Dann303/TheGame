package exceptions;

public class MovementException extends GameActionException{

	public MovementException() {
		super();
	}

	public MovementException(String message) {
		super(message);
	}

	public MovementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}