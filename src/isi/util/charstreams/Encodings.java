package isi.util.charstreams;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class Encodings {

	///////////////////////////////////////////////////////
	public static final Charset
			  UTF8 = Charset.forName("UTF-8")
			, ISO8859_1 = Charset.forName("ISO8859-1");

	///////////////////////////////////////////////////////
	//
	public static void FullEncode (final CharsetEncoder enc, final CharBuffer in, final ByteBuffer out) {
		enc.reset();

		final CoderResult encodingResult = enc.encode(in, out, true);
		if (!encodingResult.isUnderflow())
			throw new RuntimeException(encodingResult.toString());

		final CoderResult flushingResult = enc.flush(out);
		if (!flushingResult.isUnderflow())
			throw new RuntimeException(flushingResult.toString());
	}

	private final static CharBuffer cbuf1 = CharBuffer.allocate(1);
	public static void FullEncode1 (final CharsetEncoder enc, final char in, final ByteBuffer out) {
		cbuf1.clear();
		cbuf1.put(in);
		cbuf1.flip();
		assert cbuf1.remaining() == 1;
		FullEncode(enc, cbuf1, out);
	}

	public static void FullDecode (final CharsetDecoder dec, final ByteBuffer in, final CharBuffer out) {
		dec.reset();

		final CoderResult decodingResult = dec.decode(in, out, true);
		if (!decodingResult.isUnderflow())
			throw new RuntimeException(decodingResult.toString());

		final CoderResult flushingResult = dec.flush(out);
		if (!flushingResult.isUnderflow())
			throw new RuntimeException(flushingResult.toString());
	}

	///////////////////////////////////////////////////////
	// private
	private Encodings () {
	}
}
