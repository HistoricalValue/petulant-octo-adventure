package isi.util.reflect;

public class Callers {
	
	///////////////////////////////////////////////////////
	//
	/**
	 * Essentially, cannot be called from Main. Or maybe it can, and then we
	 * can find out who calls Main().
	 * @return 
	 */
	public static Class<?> DetectCallerClass () {
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		try {
			return Class.forName(stackTrace[stackTrace.length - 1 - 1 - 1].getClassName());
		} catch (final ClassNotFoundException ex) {
			throw new AssertionError(ex);
		}
	}
	
	///////////////////////////////////////////////////////
	// private
	private Callers () {
	}
}
