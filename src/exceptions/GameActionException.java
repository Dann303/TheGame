package exceptions;

public abstract class GameActionException extends Exception {

	public GameActionException() {
		super();
	}

	public GameActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}

	
	public GameActionException(String message) {
		super(message);
	}

}
