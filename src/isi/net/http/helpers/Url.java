package isi.net.http.helpers;

import isi.util.Ref;
import isi.util.charstreams.Encodings;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Url {
	
	///////////////////////////////////////////////////////
	//
	public static final Charset UrlEncoding = Encodings.UTF8;
	
	///////////////////////////////////////////////////////
	//
	public static String ParseUrlEncoded (final char[] chars, final Ref<Integer> off) {
		final ByteBuffer buf = ByteBuffer.allocate(chars.length * 2);
		buf.position(0);
		
		for (int i = off.Deref(); i < chars.length && chars[i] == '%'; i += 3, off.Assign(i)) {
			final int b = Integer.parseInt(new String(new char[]{chars[i+1], chars[i+2]}), 0x10);
			assert b <= 0xff && b >= 0;

			buf.put((byte) b);
		}
		
		buf.flip();
		return UrlEncoding.decode(buf).toString();
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
		return
				// is a 2byte character -- must be escaped through some encoding
				c > 0xff ||
				// is outside the ascii range
				c > 0x7f ||
				// is an iso8859-1 control character
				(c < 0x1f || c == 0x7f) ||
				// is a URL reserved char
				(c == '$' || c == '&' || c == '+' || c == ',' || c == '/' || c == ':' || c == ';' || c == '=' || c == '?' || c == '@') ||
				// "unsafe" characters
				(c == ' ' || c == '"' || c == '<' || c == '>' || c == '#' || c == '%' || c == '{' || c == '}' || c == '|' || c == '\\' || c == '^' || c == '~' || c == '[' || c == ']' || c == '`')
				;
	}
	
	public static String EscapeUrl (final String url) {
		final StringBuilder bob = new StringBuilder(1 << 14);
		
		for (final byte b: url.getBytes(UrlEncoding))
			bob.append("%").append(ByteToHexString(b));

		return bob.toString();
	}
	
	///////////////////////////////////////////////////////
	// private
	private Url () {
	}
}
