package isi.util.streams;

import isi.data_structures.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ByteBufferInputStream extends InputStream {

    // State
    protected final ByteBuffer buf;
    /**
     * The buffer is not supposed to be altered while for as long as it's used
     * by this stream.
     *
     * @param _buf
     */
    public ByteBufferInputStream(final ByteBuffer _buf) {
        buf = _buf;
    }

    private final byte[] p_read_buf = new byte[1];
    @Override
    public int read() throws IOException {
        final InputStream delegate = this;
        int result;
        final int read = delegate.read(p_read_buf, 0, 1);
        if (read == -1)
            result = read;
        else {
            assert read == 1;
            result = p_read_buf[0];
        }
        return result;
    }

    @Override
    public int available() throws IOException {
        return buf.available();
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public synchronized void mark(final int readlimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read(final byte[] b) throws IOException {
        final InputStream delegate = this;
        return delegate.read(b, 0, b.length);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int bufread = buf.read(b, off, len);
        final int result = bufread == 0? -1 : bufread;
        return result;
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("no supported");
    }

    @Override
    public long skip(long n) throws IOException {
        return buf.skip((int)n);
    }
    private static final Logger LOG = Logger.getLogger(ByteBufferInputStream.class.getName());



}
