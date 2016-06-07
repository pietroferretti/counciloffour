package it.polimi.ingsw.ps14.exceptions;

public class IllegalActionException extends RuntimeException {

	private static final long serialVersionUID = 2345802905167873465L;

	public IllegalActionException() {
		super();
	}

	public IllegalActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalActionException(String message) {
		super(message);
	}

	public IllegalActionException(Throwable cause) {
		super(cause);
	}

}
