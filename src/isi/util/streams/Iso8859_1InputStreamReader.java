package isi.util.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class Iso8859_1InputStreamReader extends Reader {

	///////////////////////////////////////////////////////
	// state
	private final InputStream ins;
	// utils
	private final CharsetDecoder dec = Charset.forName("ISO8859-1").newDecoder();
	
	///////////////////////////////////////////////////////
	// constructors
	public Iso8859_1InputStreamReader (final InputStream ins) {
		this.ins = ins;
	}
	
	///////////////////////////////////////////////////////
	//
	@Override
	public int read (final char[] cbuf, final int off, final int len) throws IOException {
		final byte[] buf = new byte[len];
		final int bytesRead = ins.read(buf);
		assert bytesRead <= len;
		
		if (bytesRead > 0) {
			final CharBuffer charbuf = CharBuffer.wrap(cbuf, off, bytesRead);
			charbuf.clear();
			final ByteBuffer bytebuf = ByteBuffer.wrap(buf, 0, bytesRead);
			bytebuf.clear();

			final CoderResult decodingResult = dec.reset().decode(bytebuf, charbuf, true);
			assert decodingResult == CoderResult.UNDERFLOW;
			final CoderResult flushingResult = dec.flush(charbuf);
			assert flushingResult == CoderResult.UNDERFLOW;

			assert charbuf.remaining() == 0;
		}
		
		return bytesRead;
	}

	///////////////////////////////////////////////////////
	//
	@Override
	public void close () throws IOException {
		ins.close();
	}
	
}
