package isi.util;

import isi.data_structures.ByteCyclicBuffer;
import isi.data_structures.StorageException;

public abstract class ByteArrayTransformer implements Cloneable {

    private abstract class transformOpDelegate {
        protected final ByteArrayTransformer trans;
        protected transformOpDelegate(final ByteArrayTransformer _trans) {
            trans = _trans;
        }
        public abstract int do_transform_op(final byte[] block, final int off,
                final int len, byte[] outbuf, final int outbuf_off,
                final int outbuf_len)
                throws
                TransformationException;
    }
    private final transformOpDelegate transformDelegate = new transformOpDelegate(this) {
            @Override
            public int do_transform_op(final byte[] block, final int off,
                    final int len, final byte[] outbuf, final int outbuf_off,
                    final int outbuf_len)
                throws TransformationException
            {
                return trans.transform(block, off, len, outbuf, outbuf_off, outbuf_len);
            }
        };
    private final transformOpDelegate transformFinalDelegate = new transformOpDelegate(this) {
            @Override
            public int do_transform_op(final byte[] block, final int off,
                    final int len, final byte[] outbuf, final int outbuf_off,
                    final int outbuf_len)
                throws TransformationException
            {
                return trans.transformFinal(block, off, len, outbuf, outbuf_off, outbuf_len);
            }
        };


    private byte[] p_fillBuffer_transoutbuf = new byte[1];
    private void p_fillBuffer(final byte[] block, final int off, final int len,
            final transformOpDelegate transop)
    {
        if (len < 0)
            throw new IllegalArgumentException("len(" + len + ") < 0");
        if (off < 0 || off + len > block.length)
            throw new ArrayIndexOutOfBoundsException("off(" + off + ") < 0 || " +
                    "off(" + off + ") >= block.length(" + block.length + ")");
        final int min_outbuf_len = java.lang.Math.max(len, 1);
        p_fillBuffer_transoutbuf =  p_fillBuffer_transoutbuf.length > min_outbuf_len?
                p_fillBuffer_transoutbuf : new byte[min_outbuf_len];
        int _transformed_bytes;
        while (true) {
            try {
                 _transformed_bytes = transop.do_transform_op(block, off, len,
                         p_fillBuffer_transoutbuf, 0,
                         p_fillBuffer_transoutbuf.length);
                 break;
            } catch (final OutputBufferTooShortException ex) {
                p_fillBuffer_transoutbuf =
                        new byte[(int)(p_fillBuffer_transoutbuf.length * 1.2)];
            } catch (final TransformationException ex) {
                throw new AssertionError(ex);
            }
        }

        final int transformed_bytes = _transformed_bytes;
        final byte[] transformed = p_fillBuffer_transoutbuf;
        if (! p_bufferFitsLength(transformed_bytes))
            p_expandBuffer(transformed_bytes);
        try {
            buffer.append(transformed, 0, transformed_bytes);
        } catch (final StorageException ex) {
            throw new AssertionError(ex);
        }
    }

    /**
     * @param block
     * @param off
     * @param len
     * @throws ArrayIndexOutOfBoundsException if off &lt; 0 or &gt;= block.length
     * @throws IllegalArgumentException if len &lt; 0
     */
    public void update(final byte[] block, final int off, final int len) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        p_fillBuffer(block, off, len, transformDelegate);
    }
    public void updateFinal(final byte[]block, final int off, final int len) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        p_fillBuffer(block, off, len, transformFinalDelegate);
    }
    public void update(final byte[] block) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        update(block, 0, block.length);
    }
    public void updateFinal(final byte[]block) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        updateFinal(block, 0, block.length);
    }

    public int read(final byte[] dest, final int off, final int len) {
        return buffer.read(dest, off, len);
    }

    public int available() {
        return buffer.available();
    }

    /**
     * Takes a block of bytes and transforms them into a new block of bytes.
     *
     * No output might be available until the transformFinal() method has
     * been called.
     *
     * NOTICE!!! If an exception is thrown, then it is guaranteed that no change
     * has been made in the (visible) state (and/or behaviour) of the underlying
     * implementation.
     *
     * Note: when transform() is called again after an OutputBufferTooShortException
     * exception has been thrown, then it is expected that the same input bytes
     * are used.
     *
     * @param inbuf
     * @param inbuf_off
     * @param len
     * @param outbuf
     * @param outbuf_off
     * @param outbuf_len
     * @return the number of byte written to outbuf
     * @throws TransformationException
     */
    abstract protected int transform(final byte[] inbuf, final int inbuf_off, final int len, final byte[] outbuf, final int outbuf_off, final int outbuf_len) throws TransformationException;
    /**
     * TransformFinal() can be called only with the last chunk of data. Its
     * bahaviour is undefined if it's called again with a different chunk of data.
     * If a TransformationException is thrown and transformFinal() is called
     * again with a larger buffer size, the same input bytes are expected to
     * be passed. Otherwise, its behaviour is undefined.
     *
     * @param inbuf
     * @param inbuf_off
     * @param len
     * @param outbuf
     * @param outbuf_off
     * @param outbuf_len
     * @return
     * @throws TransformationException
     */
    abstract protected int transformFinal(final byte[] inbuf, final int inbuf_off, final int len, final byte[] outbuf, final int outbuf_off, final int outbuf_len) throws TransformationException;

    // on-state operations
    private boolean p_bufferFitsLength(final int length) {
        return length <= buffer.freeSpace();
    }
    private void p_expandBuffer(final int at_least_for) {
        final ByteCyclicBuffer buf = new ByteCyclicBuffer(buffer.capacity() + at_least_for);
        assert buf.available() == 0;
        final byte[] medium = new byte[buffer.available()];
        final Integer old_buffer_available = buffer.available();
        buffer.read(medium, 0, medium.length);
        assert buffer.available() == 0;
        try { buf.append(medium); }
        catch (final StorageException ex) { throw new AssertionError(ex); }
        assert buf.available() == old_buffer_available;
        buffer = buf;
    }

    // State
    private ByteCyclicBuffer buffer = new ByteCyclicBuffer(3);
    //
    private void p_setBuffer(final ByteCyclicBuffer _buffer) {
        buffer = _buffer;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public ByteArrayTransformer clone() throws CloneNotSupportedException {
        final ByteArrayTransformer result = (ByteArrayTransformer) super.clone();
        result.p_setBuffer(buffer.clone());
        return result;
    }

}
