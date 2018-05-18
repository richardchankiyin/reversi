package com.richard;

public class InvalidMoveException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5313975865184312140L;

	public InvalidMoveException() {
		this("InvalidMoveException");
	}

	public InvalidMoveException(String message) {
		super(message);
	}

	public InvalidMoveException(Throwable cause) {
		super(cause);
	}

	public InvalidMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMoveException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
