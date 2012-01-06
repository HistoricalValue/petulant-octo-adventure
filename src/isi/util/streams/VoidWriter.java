package isi.util.streams;

import java.io.IOException;

public class VoidWriter extends java.io.Writer {

	private boolean closed;

	/**
	 * 
	 * @param cbuf
	 * @param off
	 * @param len
	 * @throws IOException						if writer has been closed
	 * @throws ArrayIndexOutOfBoundsException	if {@code off < 0 || len < 0 || off >= cbuf.length || off + len >= cbuf.length}
	 */
	@Override
	public void write (char[] cbuf, int off, int len) throws IOException, ArrayIndexOutOfBoundsException {
		final int illegalIndex = 
				off < 0? off :
				len < 0? len :
				off >= cbuf.length? off :
				off + len >= cbuf.length? off + len :
				-1;
		if (illegalIndex == -1)
			flush (); // check for closed writer through flush()
		else
			throw new ArrayIndexOutOfBoundsException(illegalIndex);
	}

	/**
	 * 
	 * @throws IOException if writer has been closed
	 */
	@Override
	public void flush () throws IOException {
		if (closed)
			throw new IOException("writer closed");
	}

	/**
	 * 
	 * @throws IOException if writer has been closed
	 */
	@Override
	public void close () throws IOException {
		flush();
		closed = true;
	}
	
}
