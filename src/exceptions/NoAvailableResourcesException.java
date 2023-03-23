package exceptions;

public class NoAvailableResourcesException extends GameActionException {

	public NoAvailableResourcesException() {
		super();
	}

	public NoAvailableResourcesException(String message) {
		super(message);
	}

	// w mesh fahem bardo el constructor dah beye3mel eh
	public NoAvailableResourcesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}