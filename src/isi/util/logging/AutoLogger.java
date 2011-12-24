package isi.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amalia
 */
public class AutoLogger {
	public <T> AutoLogger (Class<T> klass) {
		logger = Logger.getLogger(klass.getCanonicalName());
	}

	public void p (final Object o) {
		logger.log(Level.INFO, o.toString());
	}

	@SuppressWarnings("NonConstantLogger")
	private final Logger logger;
}
