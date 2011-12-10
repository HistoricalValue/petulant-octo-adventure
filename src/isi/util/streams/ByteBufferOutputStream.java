package isi.util.streams;

import isi.data_structures.ByteBuffer;
import isi.data_structures.StorageException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class ByteBufferOutputStream extends OutputStream {

    protected final ByteBuffer buf;
    public ByteBufferOutputStream(final ByteBuffer _buf) {
        buf = _buf;
    }

    public ByteBuffer getUnderlyingBuffer() {
        return buf;
    }
    
    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void write(final byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        try {
            buf.append(b, off, len);
        } catch (final StorageException ex) {
            throw new IOException(ex);
        }
    }

    private final byte[] p_write_int_buf = new byte[1];
    @Override
    public void write(final int _b) throws IOException {
        final byte b = (byte)_b;
        if (b != _b)
            throw new IOException("b is not a valid byte (0x00 - 0xff). b=" +
                    Integer.toHexString(_b));
        p_write_int_buf[0] = b;
        write(p_write_int_buf, 0, 1);
    }
    private static final Logger LOG = Logger.getLogger(ByteBufferOutputStream.class.getName());

}
