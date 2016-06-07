package it.polimi.ingsw.ps14.exceptions;

public class InvalidSettingsException extends RuntimeException {

	private static final long serialVersionUID = -2660178310233131458L;

	public InvalidSettingsException() {
		super();
	}

	public InvalidSettingsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidSettingsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSettingsException(String message) {
		super(message);
	}

	public InvalidSettingsException(Throwable cause) {
		super(cause);
	}
	
}
