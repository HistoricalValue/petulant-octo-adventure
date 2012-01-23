package isi.net.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public abstract class Response {
	
	///////////////////////////////////////////////////////
	// state
	private final ResponseHeader header = new ResponseHeader();
	private final WritableByteChannel client;
	
	///////////////////////////////////////////////////////
	//
	public abstract Response SetStatus (final Status status);
	public abstract Response SetContentLength (final long contentLength);
	public abstract Response SetContentType (final ContentType contentType);
	public abstract Response SetEncoding (final Charset encoding);
	
	public abstract Response WriteWhole (final ByteBuffer buf);
	
	///////////////////////////////////////////////////////
	// protected
	///////////////////////////////////////////////////////
	protected Response SetStatusImpl (final Status status) {
		header.SetStatus(status);
		return this;
	}
	
	protected Response SetContentLengthImpl (final long contentLength) {
		header.SetContentLength(contentLength);
		return this;
	}
	
	protected Response SetContentTypeImpl (final ContentType contentType) {
		header.SetContentType(contentType);
		return this;
	}
	
	protected Response SetEncodingImpl (final Charset encoding) {
		header.SetEncoding(encoding);
		return this;
	}
	
	///////////////////////////////////////////////////////
	//
	protected Response WriteWholeImpl (final ByteBuffer buf) throws IOException {
		while (buf.hasRemaining())
			client.write(buf);
		return this;
	}
	
	///////////////////////////////////////////////////////
	// constructors
	protected Response (final WritableByteChannel client) {
		this.client = client;
	}
	
}
