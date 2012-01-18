package isi.util;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 *
 * @author Amalia
 */
public class Throwables {

	private Throwables () {
	}

	public static String toString (final Throwable t) {
		final CharArrayWriter caw = new CharArrayWriter(1 << 14);

		final PrintWriter w = new PrintWriter(caw);
		t.printStackTrace(w);

		w.close();
		caw.close();

		return caw.toString();
	}
}
