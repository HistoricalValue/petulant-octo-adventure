package isi.util;

public class StringBuilders {

	public static void reset (final StringBuilder bob) {
		bob.delete(0, Integer.MAX_VALUE);
	}

	public static boolean isEmpty (final StringBuilder bob) {
		return bob.length() == 0;
	}

	private StringBuilders () {
	}
}
