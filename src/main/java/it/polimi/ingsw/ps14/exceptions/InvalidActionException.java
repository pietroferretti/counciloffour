package it.polimi.ingsw.ps14.exceptions;

public class InvalidActionException extends RuntimeException {

	private static final long serialVersionUID = 5923858682834858345L;

	public InvalidActionException() {
		super();
	}

	public InvalidActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidActionException(String message) {
		super(message);
	}

	public InvalidActionException(Throwable cause) {
		super(cause);
	}

}
