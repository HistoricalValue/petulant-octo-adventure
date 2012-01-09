package isi.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

public class Charstreams {
	
	private Charstreams () {
	}
	
	@SuppressWarnings("NestedAssignment")
	public static void transfuse (final Reader r, final Writer w) throws IOException {
        final char buf[] = new char[1 << 20];
        int read = -1;
        while ((read = r.read(buf)) != -1)
            w.write(buf, 0, read);
        w.flush();
	}
	
	public static void transfuse (final Reader r, final Appendable appendable) throws IOException {
		final CharBuffer buf = CharBuffer.allocate(1 << 19);
		
		for (buf.clear(); r.read(buf) != -1; buf.clear()) {
			buf.flip();
			appendable.append(buf);
		}
	}
	
	public static char[] readAll (final Reader r) throws IOException {
		final CharArrayWriter w = new CharArrayWriter(1 << 19);
		
		transfuse(r, w);
		w.flush();
		
		return w.toCharArray();
	}
	
}
