package isi.util;

public class FactoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FactoryException (final Throwable cause) {
		super(cause);
	}

	public FactoryException (final String message, final Throwable cause) {
		super(message, cause);
	}

	public FactoryException (final String message) {
		super(message);
	}

	public FactoryException () {
	}

}
