package isi.net.frails.view;

import isi.net.frails.Viewing;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class HtmlReaderViewing implements Viewing, Closeable, AutoCloseable {

	///////////////////////////////////////////////////////
	// state
	final Reader r;

	///////////////////////////////////////////////////////
	//
	public HtmlReaderViewing (final Reader r) {
		this.r = r;
	}

	///////////////////////////////////////////////////////
	//
	@Override
	public void WriteTo (final Appendable w) throws IOException {
		isi.util.Charstreams.transfuse(r, w);
	}

	///////////////////////////////////////////////////////
	//

	/**
	 * Closes the wrapped reader.
	 * @throws IOException
	 */
	@Override
	public void close () throws IOException {
		r.close();
	}

}
