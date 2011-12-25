package isi.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amalia
 */
public class AutoLogger {
	public <T> AutoLogger (final Logger logger) {
		this.logger = logger;
	}

	public void fff (final Object o) {
		logger.log(Level.FINEST, o.toString());
	}
	
	public void i (final Object o) {
		logger.log(Level.INFO, o.toString());
	}
	
	public void w (final Object o) {
		logger.log(Level.WARNING, o.toString());
	}

	@SuppressWarnings("NonConstantLogger")
	private final Logger logger;
}
