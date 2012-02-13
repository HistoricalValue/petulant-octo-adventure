package isi.net.http;

import isi.util.Channels;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public abstract class Response implements WritableByteChannel {

	///////////////////////////////////////////////////////
	// state
	private final ResponseHeader header = new ResponseHeader();
	private final WritableByteChannel client;
	private boolean closed = false;

	///////////////////////////////////////////////////////
	// abstract
	protected abstract void closeImpl (final WritableByteChannel client) throws IOException;
	protected abstract int writeImpl (final ByteBuffer buf, final WritableByteChannel client) throws IOException;

	///////////////////////////////////////////////////////
	//
	public Response SetStatus (final Status status) throws IOException {
		ensureOpen();
		header.SetStatus(status);
		return this;
	}

	public Response SetContentLength (final long contentLength) throws IOException {
		ensureOpen();
		header.SetContentLength(contentLength);
		return this;
	}

	public Response SetContentType (final ContentType contentType) throws IOException {
		ensureOpen();
		header.SetContentType(contentType);
		return this;
	}

	public Response SetEncoding (final Charset encoding) throws IOException {
		ensureOpen();
		header.SetEncoding(encoding);
		return this;
	}

	public Response SetContentDisposition (final String filename) throws IOException {
		ensureOpen();
		header.SetContentDisposition(filename);
		return this;
	}
	
	public Response SetMd5 (final String md5) throws IOException {
		ensureOpen();
		header.SetMd5(md5);
		return this;
	}

	///////////////////////////////////////////////////////
	//
	@Override
	public void close () throws IOException {
		if (!closed)
			closeImpl(client);
		closed = true;
	}

	@Override
	public boolean isOpen () {
		return !closed;
	}

	@Override
	public int write (final ByteBuffer buf) throws IOException {
		ensureOpen();
		return writeImpl(buf, client);
	}

	///////////////////////////////////////////////////////
	// protected
	///////////////////////////////////////////////////////

	///////////////////////////////////////////////////////
	//
	protected Response WriteHeader () throws IOException {
		ensureOpen();
		try (final Writer w = Channels.newUnclosableWriter(client, Request.Encoding.newEncoder(), 256)) {
			header.WriteTo(w);
		}
		return this;
	}

	///////////////////////////////////////////////////////
	// constructors
	protected Response (final WritableByteChannel client) {
		this.client = client;
	}

	///////////////////////////////////////////////////////
	// private
	///////////////////////////////////////////////////////

	///////////////////////////////////////////////////////
	//
	private void ensureOpen () throws IOException {
		if (closed)
			throw new IOException("closed");
	}
}
