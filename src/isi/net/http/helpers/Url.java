package isi.net.http.helpers;

import isi.util.Ref;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Url {
	
	///////////////////////////////////////////////////////
	//
	public static String ParseUrlEncoded (final char[] chars, final Ref<Integer> off, final Charset charset) {
		final ByteBuffer buf = ByteBuffer.allocate(chars.length);
		buf.position(0);
		
		for (int i = off.Deref();  i < chars.length && chars[i] == '%'; i += 3, off.Assign(i)) {
			final int b = Integer.parseInt(new String(new char[]{chars[i+1], chars[i+2]}), 0x10);
			assert b <= 0xff && b >= 0;

			buf.put((byte) b);
		}

		buf.flip();
		if (buf.hasArray())
			return new String(buf.array(), 0, buf.limit(), charset);
		else {
			final byte[] bytes = new byte[buf.limit()];
			buf.get(bytes);
			return new String(bytes, charset);
		}
			
	}
	
	public static String ParseUrl (final String url, final Charset charset) {
		final StringBuilder bob = new StringBuilder(url.length());
		final char[] chars = url.toCharArray();
		
		final Ref<Integer> ref = Ref.CreateRef(0);
		for (int i = 0; i < chars.length; i = ref.Deref())
			if (chars[i] == '%') {
				ref.Assign(i);
				bob.append(ParseUrlEncoded(chars, ref, charset));
			}
			else {
				ref.Assign(i + 1);
				bob.append(chars[i]);
			}
		
		return bob.toString();
	}
	
	///////////////////////////////////////////////////////
	// private
	private Url () {
	}
}
