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
	
	private Strings () {
	}
	
	public static String Escape (final String str) {
		return str.replace("\"", "\\\"").replace("\n", "\\\n");
	}
	public static String Escape (final Object o) {
		return Escape(o.toString());
	}
	
	public static String ToJavaLiteral (final Reader r) throws IOException {
		final StringBuilder bob = new StringBuilder(1 << 14);
		final BufferedReader br = new BufferedReader(r);
		
		bob.append("\"\"");
		
		for (String line = br.readLine(); line != null; line = br.readLine())
			bob.append("\n+ \"").append(Escape(line)).append("\\n\"");
		
		bob.append(";");
		
		return bob.toString();
	}
	
	public static <T> String Join (final Iterable<? extends T> iterable, final String joint) {
		final Iterator<? extends T> ite = iterable.iterator();
		
		if (!ite.hasNext())
			return "";
		
		{
			final StringBuilder bob = new StringBuilder(1 << 14);
			
			bob.append(ite.next().toString());
			
			while (ite.hasNext())
				bob.append(joint).append(ite.next().toString());
			
			return bob.toString();
		}
	}
}
