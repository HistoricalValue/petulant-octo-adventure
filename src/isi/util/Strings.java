package isi.util;

/**
 *
 * @author Amalia
 */
public class Strings {
	
	private Strings () {
	}
	
	public static String Escape (final String str) {
		return str.replace("\"", "\\\"").replace("\n", "\\\n");
	}
	public static String Escape (final Object o) {
		return Escape(o.toString());
	}
}
