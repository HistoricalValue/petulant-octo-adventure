package isi.util.charstreams;

import java.io.IOException;
import java.io.Writer;

public class MultiWriterDelegate extends Writer {

	///////////////////////////////////////////////////////
	// state
	private final Iterable<? extends Writer> writers;
	
	///////////////////////////////////////////////////////
	//
	public MultiWriterDelegate (final Iterable<? extends Writer> writers) {
		this.writers = isi.util.Collections.newUnmodifiableList(writers);
	}
	public MultiWriterDelegate (Writer... writers) {
		this(java.util.Arrays.asList(writers));
	}
	
	@Override
	public Writer append (final CharSequence csq) throws IOException {
		for (final Writer a: writers)
			a.append(csq);
		return this;
	}

	@Override
	public Writer append (final CharSequence csq, final int start, final int end) throws IOException {
		for (final Writer a: writers)
			a.append(csq, start, end);
		return this;
	}

	@Override
	public Writer append (final char c) throws IOException {
		for (final Writer a: writers)
			a.append(c);
		return this;
	}

	@Override
	public void write (final char[] cbuf, final int off, final int len) throws IOException {
		for (final Writer a: writers)
			a.write(cbuf, off, len);
	}

	@Override
	public void flush () throws IOException {
		for (final Writer a: writers)
			a.flush();
	}

	@Override
	public void close () throws IOException {
		for (final Writer a: writers)
			a.close();
	}
	
}
