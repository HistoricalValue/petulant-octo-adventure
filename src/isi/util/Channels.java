package isi.util;

import java.io.FilterOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
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
	
	public static int transfuse (final ReadableByteChannel chin, final WritableByteChannel chout) throws IOException {
		final ByteBuffer buf = ByteBuffer.allocate(1 << 17); // 256 KiB
		
		int totalbytesread = 0;
		for (int bytesread = chin.read(buf); bytesread != -1; buf.clear(), bytesread = chin.read(buf)) {
			buf.flip();
			totalbytesread += buf.remaining();
			writeWhole(buf, chout);
		}
		
		return totalbytesread;
	}
	
	///////////////////////////////////////////////////////
	// private
	private Channels () {
	}
}
