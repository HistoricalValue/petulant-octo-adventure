package isi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 *
 * @author Amalia
 */
public class Strings {

	///////////////////////////////////////////////////////
	// constructor
	private Strings () {
	}

	///////////////////////////////////////////////////////
	//
	public static String Escape (final String str) {
		return str.replace("\"", "\\\"").replace("\r", "").replace("\n", "\\\n");
	}
	public static String Escape (final Object o) {
		return Escape(o.toString());
	}

	///////////////////////////////////////////////////////
	//
	public static String ToJavaLiteral (final Reader r) throws IOException {
		final StringBuilder bob = new StringBuilder(1 << 14);
		final BufferedReader br = new BufferedReader(r);

		bob.append("\"\"");

		for (String line = br.readLine(); line != null; line = br.readLine())
			bob.append("\n+ \"").append(Escape(line)).append("\\n\"");

		bob.append(";");

		return bob.toString();
	}

	///////////////////////////////////////////////////////
	//
	public static <T> String Join (final Iterable<? extends T> iterable, final String joint, final Stringifier stringifier) {
		final Iterator<? extends T> ite = iterable.iterator();

		if (!ite.hasNext())
			return "";

		{
			final StringBuilder bob = new StringBuilder(1 << 14);

			bob.append(stringifier.ToString(ite.next()));

			while (ite.hasNext())
				bob.append(joint).append(stringifier.ToString(ite.next()));

			return bob.toString();
		}
	}

	public static <T> String Join (final Iterable<? extends T> iterable, final String joint) {
		return Join(iterable, joint, new Stringifier());
	}
	
	public static <T> String Join (final T[] array, final String joint, final Stringifier stringifier) {
		return Join(java.util.Arrays.asList(array), joint, stringifier);
	}
	
	public static <T> String Join (final T[] array, final String joint) {
		return Join(array, joint, new Stringifier());
	}
	
	///////////////////////////////////////////////////////
	//
	public static String Replicate (final String what, final int times) {
		final StringBuilder bob = new StringBuilder(what.length() * times);
		for (int i = 0; i < times; ++i)
			bob.append(what);
		return bob.toString();
	}
	
	///////////////////////////////////////////////////////
	//
	public static String ToString (final Object o) {
		return new Stringifier().ToString(o);
	}
}
