package isi.util;

import java.io.Closeable;
import java.io.Flushable;

public class Formatter implements Closeable, Flushable, AutoCloseable, Appendable {

	///////////////////////////////////////////////////////
	// state
	private final java.util.Formatter f;
	
	///////////////////////////////////////////////////////
	// constructors
	public Formatter (final Appendable app) {
		f = new java.util.Formatter(app);
	}

	///////////////////////////////////////////////////////
	//
	public Formatter format (final String fmt, final Object... params) {
		f.format(fmt, params);
		return this;
	}
	
	@Override
	public void close () {
		f.close();
	}

	@Override
	public void flush () {
		f.flush();
	}

	@Override
	public Formatter append (final CharSequence csq) {
		f.format("%s", csq.toString());
		return this;
	}

	@Override
	public Appendable append (final CharSequence csq, final int start, final int end) {
		return append(csq.subSequence(start, end));
	}

	@Override
	public Appendable append (final char c) {
		f.format("%c", c);
		return this;
	}
	
}
