package isi.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class BufferedResponse extends Response {

	///////////////////////////////////////////////////////
	// state
	private final ByteArrayOutputStream baouts = new ByteArrayOutputStream(1 << 14);
	private final WritableByteChannel buffer = Channels.newChannel(baouts);
	
	///////////////////////////////////////////////////////
	// constructors
	public BufferedResponse (final WritableByteChannel client) {
		super(client);
	}
	
	///////////////////////////////////////////////////////
	// Implementing Response
	@Override
	public Response SetContentLength (final long contentLength) throws IOException {
		throw new UnsupportedOperationException("Not allowed -- set automatically");
	}

	///////////////////////////////////////////////////////
	// Implementing WritableByteChannel
	@Override
	protected int writeImpl (final ByteBuffer src, final WritableByteChannel client) throws IOException {
		return isi.util.Channels.writeWhole(src, buffer);
	}

	@Override
	protected void closeImpl (final WritableByteChannel client) throws IOException {
		// finalise body
		buffer.close();
		baouts.close();
		// update header according to body
		super.SetContentLength(baouts.size());
		// write header to client
		WriteHeader();
		// write body to client
		try (final OutputStream outs = isi.util.Channels.newUnclosableOutputStream(client)) {
			baouts.writeTo(outs);
		}
	}
}
