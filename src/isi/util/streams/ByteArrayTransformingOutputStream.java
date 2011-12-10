package isi.util.streams;

import isi.util.ByteArrayTransformer;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class ByteArrayTransformingOutputStream extends FilterOutputStream {

    public static final int DEFAULT_BUF_LENGTH = 8*1024;
    
    /// Layer 3 - uses self public API (aliases)
    @Override
    public void write(final byte[] b) throws IOException {
        write(b, 0, b.length);
    }
    private final byte[] p_write_buf = new byte[1];
    @Override
    public void write(final int b) throws IOException {
        if ((b & ~0xff) != 0) {
            assert ((byte)b) == b;
            throw new IOException("Integer 0x" + Integer.toHexString(b) +
                    " not in byte range (0x00 - 0xff)");
        }
        p_write_buf[0] = (byte)b;
        write(p_write_buf);
    }

    /// Layer 2 - uses self private API (core) -- Checks for closed stream before allowing operations
    private final byte[] p_close_zero_buf = new byte[0];
    @Override
    public void close() throws IOException {
        p_failIfClosed();
        p_updateFinallyTransformer(p_close_zero_buf, 0, 0);
        p_writeAvailableBytes();
        closed = true;
        out.close();
    }
    //
    @Override
    public void flush() throws IOException {
        p_failIfClosed();
        p_writeAvailableBytes();
        out.flush();
    }
    //
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        p_failIfClosed();
        p_updateAndWriteAvailable(b, off, len);
    }
    //
    public static ByteArrayTransformingOutputStream createInstance(final ByteArrayTransformer trans, final OutputStream outs) {
        return new ByteArrayTransformingOutputStream(trans, outs);
    }

    /// Layer 1 - private API (core support) -- operations DO NOT check for closed stream
    private byte[] p_copybuf = new byte[DEFAULT_BUF_LENGTH];
    private int p_copybuf_read = 0;
    private void p_writeAvailableBytes() throws IOException {
        if (!p_copybufFitsAvailableBytes())
            p_expandCopyBuf();
        p_readTransformer();
        p_flushCopybuf();
    }
    private void p_updateAndWriteAvailable(final byte[] b, final int off, final int len) throws IOException {
        p_updateTransformer(b, off, len);
        p_writeAvailableBytes();
    }
    /// Layer 0 - does not break down any more
    private void p_flushCopybuf() throws IOException {
        out.write(p_copybuf, 0, p_copybuf_read);
    }
    private void p_readTransformer() {
        p_copybuf_read = trans.read(p_copybuf, 0, p_copybuf.length);
    }
    private void p_updateTransformer(final byte[] b, final int off, final int len) {
        trans.update(b, off, len);
    }
    private void p_updateFinallyTransformer(final byte[] b, final int off, final int len) throws IOException {
        trans.updateFinal(b, off, len);
    }
    private boolean p_copybufFitsAvailableBytes() {
        return trans.available() <= p_copybuf.length;
    }
    private void p_expandCopyBuf() {
        final byte[] nucopybuf = new byte[(int)((java.lang.Math.max(p_copybuf.length, 1)) * 1.2)];
        System.arraycopy(p_copybuf, 0, nucopybuf, 0, p_copybuf_read);
        p_copybuf = nucopybuf;
    }
    private void p_failIfClosed() throws IOException {
        if (closed)
            throw new IOException("Stream closed");
    }
    private ByteArrayTransformingOutputStream(final ByteArrayTransformer _trans, final OutputStream outs) {
        super(outs);
        trans = _trans;
    }
    // State
    private ByteArrayTransformer trans;
    private boolean closed = false;
    private static final Logger LOG = Logger.getLogger(ByteArrayTransformingOutputStream.class.getName());

}
