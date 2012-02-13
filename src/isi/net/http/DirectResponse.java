package isi.net.http;

import isi.util.Channels;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class DirectResponse extends Response {

	///////////////////////////////////////////////////////
	// state
	private boolean headerFinalised = false;
	private Long contentLength;
	private int totalBytesWritten = 0;

	///////////////////////////////////////////////////////
	// constructors
	public DirectResponse (final WritableByteChannel client) {
		super(client);
	}

	///////////////////////////////////////////////////////
	//
	@Override
	public Response SetStatus (final Status status) throws IOException {
		ensureHeaderNotWritten();
		return super.SetStatus(status);
	}

	@Override
	public Response SetContentLength (final long contentLength) throws IOException {
		ensureHeaderNotWritten();
		this.contentLength = contentLength;
		return super.SetContentLength(contentLength);
	}

	@Override
	public Response SetContentType (final ContentType contentType) throws IOException {
		ensureHeaderNotWritten();
		return super.SetContentType(contentType);
	}

	@Override
	public Response SetEncoding (final Charset encoding) throws IOException {
		ensureHeaderNotWritten();
		return super.SetEncoding(encoding);
	}
	
	@Override
	public Response SetContentDisposition (final String filename) throws IOException {
		ensureHeaderNotWritten();
		return super.SetContentDisposition(filename);
	}
	
	@Override
	public Response SetMd5 (final String md5) throws IOException {
		ensureHeaderNotWritten();
		return super.SetMd5(md5);
	}

	///////////////////////////////////////////////////////
	//
	@Override
	protected void closeImpl (final WritableByteChannel client) throws IOException {
	}

	@Override
	protected int writeImpl (final ByteBuffer buf, final WritableByteChannel client) throws IOException {
		if (!headerFinalised) {
			if (contentLength == null)
				throw new IOException("Content-length has not been set");
			headerFinalised = true;
			WriteHeader();
		}

		if (totalBytesWritten + buf.remaining() > contentLength)
			throw new IOException("Writing more bytes than declared by Content-length");

		final int bytesWritten = Channels.writeWhole(buf, client);
		totalBytesWritten += bytesWritten;
		return bytesWritten;
	}


	///////////////////////////////////////////////////////
	//
	private void ensureHeaderNotWritten () throws IOException {
		if (headerFinalised)
			throw new IOException("header already written to client");
	}
}
