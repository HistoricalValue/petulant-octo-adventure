package isi.util.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class CopyOnReadInputStream extends InputStream {

    /// State
    private final InputStream in;
    private final OutputStream out;
    public CopyOnReadInputStream(final InputStream _in, final OutputStream _out) {
        in = _in;
        out = _out;
    }

//    public ByteBuffer getUnderlyingByteBuffer() {
//        final CopyOnReadInputStream delegate = this;
//        //
//        return delegate.out;
//    }

    /**
     * Resets the underlying buffer, so that it contains only data read from
     * here and now on.
     */
//    public void resetBuffer() {
//        final CopyOnReadInputStream delegate = this;
//        //
//        delegate.out.reset();
//    }

    private final byte[] read_onebytebuf = new byte[1];
    @Override
    public int read() throws IOException {
        final int result = in.read();
        if (result != -1)
            if ((result&0xff) != result)
                throw new IOException("invalid byte read from underlying input " +
                        "stream (" + in + "): byte " + result);
            else {
                read_onebytebuf[0] = (byte) result;
                out.write(read_onebytebuf, 0, 1);
            }
        return result;
    }

    @Override
    public int available() throws IOException {
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
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
        return read(b, 0, b.length);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int result = in.read(b, off, len);
        if (result != -1)
            out.write(b, off, result);
        return result;
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(final long n) throws IOException {
        return in.skip(n);
    }
    private static final Logger LOG = Logger.getLogger(CopyOnReadInputStream.class.getName());



}
