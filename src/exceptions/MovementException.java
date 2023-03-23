package exceptions;

public class MovementException extends GameActionException{

	public MovementException() {
		super();
	}

	public MovementException(String message) {
		super(message);
	}

	// w mesh fahem el constructor dah bardo
	public MovementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}