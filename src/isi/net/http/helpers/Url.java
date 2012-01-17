package isi.net.http.helpers;

import isi.util.Ref;
import isi.util.charstreams.Encodings;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Url {
	
	///////////////////////////////////////////////////////
	//
	public static final Charset UrlEncoding = Encodings.UTF8;
	
	///////////////////////////////////////////////////////
	// utils
	private static final CharBuffer cbuf1 = CharBuffer.allocate(1);
	private static final ByteBuffer bbuf1 = ByteBuffer.allocate(1);
	//
	private static final CharsetEncoder enc = Encodings.ISO8859_1.newEncoder();
	private static final CharsetDecoder dec = Encodings.ISO8859_1.newDecoder();

	///////////////////////////////////////////////////////
	//
	public static String ParseUrlEncoded (final char[] chars, final Ref<Integer> off) {
		final StringBuilder bob = new StringBuilder(1 << 14);
		
		for (int i = off.Deref(); i < chars.length && chars[i] == '%'; i += 3, off.Assign(i)) {
			final int	high = Character.getNumericValue(chars[i+1]),
						low = Character.getNumericValue(chars[i+2]);
			if (high < 0 || low < 0 || high > 0xf || low > 0xf)
				throw new IllegalArgumentException("Not a valid byte: " + chars[i+1] + chars[i + 2]);
			
			final int b = (high << 4 ) | low;
			assert b <= 0xff && b >= 0;

			bbuf1.clear();
			bbuf1.put((byte) b);
			
			cbuf1.clear();
			
			try {
				Encodings.FullDecode(dec, bbuf1, cbuf1);
			}
			catch (final RuntimeException wat) {
				throw new AssertionError("", wat);
			}
			
			assert bbuf1.remaining() == 0;
			assert cbuf1.remaining() == 1;

			bob.append(cbuf1.get());
		}
		
		return bob.toString();
	}
	
	public static String ParseUrl (final String url) {
		final StringBuilder bob = new StringBuilder(url.length());
		final char[] chars = url.toCharArray();
		
		final Ref<Integer> ref = Ref.CreateRef(0);
		for (int i = 0; i < chars.length; i = ref.Deref())
			if (chars[i] == '%') {
				ref.Assign(i);
				bob.append(ParseUrlEncoded(chars, ref));
			}
			else {
				ref.Assign(i + 1);
				bob.append(chars[i]);
			}
		
		return bob.toString();
	}
	
	public static String ByteToHexString (final int b) {
		if (b < 0 || b > 0xff)
			throw new IllegalArgumentException(String.format("%#02X", b));
		return ByteToHexString((byte) b);
	}
	
	public static String ByteToHexString (final byte b) {
		return String.format("%02X", b);
	}
	
	public static boolean MustBeUrlEscaped (final char c) {
		assert 0x00 <= c;
		return	// is an 1-byte character -- 2 byte chars are ok to be send directly as unicode
				c <= 0xff && (
					// is outside the ascii range
					c > 0x7f ||
					// is an iso8859-1 control character
					(c < 0x1f || c == 0x7f) ||
					// is a URL reserved char
					(c == '$' || c == '&' || c == '+' || c == ',' || c == '/' || c == ':' || c == ';' || c == '=' || c == '?' || c == '@') ||
					// "unsafe" characters
					(c == ' ' || c == '"' || c == '<' || c == '>' || c == '#' || c == '%' || c == '{' || c == '}' || c == '|' || c == '\\' || c == '^' || c == '~' || c == '[' || c == ']' || c == '`')
				);
	}
	
	public static String EscapeUrl (final String url) {
		final StringBuilder bob = new StringBuilder(1 << 14);
		
		for (final char c: url.toCharArray())
			if (MustBeUrlEscaped(c)) {
				cbuf1.clear();
				cbuf1.put(c);
				
				bbuf1.clear();
				
				try {
					Encodings.FullEncode(enc, cbuf1, bbuf1);
				}
				catch (final RuntimeException wat) {
					throw new AssertionError("", wat);
				}
				
				assert cbuf1.remaining() == 0;
				assert bbuf1.remaining() == 1;
				
				bob.append("%").append(ByteToHexString(bbuf1.get()));
			}
			else
				bob.append(c);

		return bob.toString();
	}
	
	///////////////////////////////////////////////////////
	// private
	private Url () {
	}
}
