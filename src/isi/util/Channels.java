package isi.util;

import java.io.FilterOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.CharsetEncoder;

public class Channels {
	
	///////////////////////////////////////////////////////
	//
	public static int writeWhole (final ByteBuffer buf, final WritableByteChannel channel) throws IOException {
		int totalWritten = 0;
		final int initialRemaining = buf.remaining();
		for (int remaining = buf.remaining(); remaining > 0; remaining = buf.remaining()) {
			final int written = channel.write(buf);
			assert written == remaining - buf.remaining();
			totalWritten += written;
		}
		assert totalWritten == initialRemaining;
		return initialRemaining;
	}
	
	public static OutputStream newUnclosableOutputStream (final WritableByteChannel channel) {
		return new FilterOutputStream(java.nio.channels.Channels.newOutputStream(channel)) {
			@Override
			public void close () throws IOException {
				flush();
			}
		};
	}
	
	public static Writer newUnclosableWriter (final WritableByteChannel channel, final CharsetEncoder enc, final int minimumCapacity) {
		return new FilterWriter(java.nio.channels.Channels.newWriter(channel, enc, minimumCapacity)) {
			@Override
			public void close () throws IOException {
				flush();
			}
		};
	}
	///////////////////////////////////////////////////////
	// private
	private Channels () {
	}
}
