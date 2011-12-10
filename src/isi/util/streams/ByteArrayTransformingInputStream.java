package isi.util.streams;

import isi.util.ByteArrayTransformer;
import isi.util.TransformationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This stream assumes that when the underlying buffer empties, EOF is reached.
 * Refilling the buffer is not guaranteed to work, since it might empty
 * before it gets the chance to be refilled.
 *
 * If you require something different than the behaviour above, make your own
 * class.
 *
 * @author isilando
 */
public class ByteArrayTransformingInputStream extends InputStream {

    private final int DEFAULT_BUF_FILLING = (8 * 1024);
    private boolean eof = false;// eof global flag also used as finalisation flag
                        // (updateFinal() must be called only once -- the first
                        // time EOF is met).
    public ByteArrayTransformingInputStream(final ByteArrayTransformer _trans, final InputStream _ins) {
        trans = _trans;
        ins = _ins;
    }
    // State
    private final ByteArrayTransformer trans;
    private final InputStream ins;

    @Override
    public int available() { return trans.available(); }

    @Override
    public int read(final byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(final byte[] buf, final int off, final int len) throws IOException {
        if (len > trans.available())
            try { p_fillTransformerFor(Math.max(len, DEFAULT_BUF_FILLING)); }
            catch (final TransformationException ex) { throw new IOException(ex); }
        final int result = trans.read(buf, off, len);
        assert (result == 0 && eof) || result != 0; // result=0  ->  eof   (BUT NOT eof -> result=0 [things might still be buffered])
        return result == 0? -1 : result;
    }

    @Override
    public int read() throws IOException {
        final byte[] buf = new byte[1];
        final int read = read(buf, 0, buf.length);
        assert (read == -1 && eof) || read != -1; // eof flag consistency
        final int result = read == -1? -1 : (((int)buf[0]) & 0xff);
        return result;
    }

    //////////////////////////////////////////////////////
    private void p_fillTransformerFor(final int len) throws IOException, TransformationException {
        final int to_fill = len - trans.available();
        assert to_fill > 0;
        byte[] buf = new byte[len/2], buf_next = new byte[len/2 + len%2];
        int read = ins.read(buf), read_next;
        boolean _eof = read == -1;
        if (!_eof) {
            read_next = ins.read(buf_next);
            _eof = read_next == -1;
            if (!_eof) {
                // we gots two blocks , start teh algorithm-loop choo choo
                while (!_eof && trans.available() < len) {
                    // process previous block either way
                    trans.update(buf, 0, read);
                    // move next to current (swap buffers to avoid copying and allocation times)
                    read = read_next;
                    {byte[] tmp = buf; buf = buf_next; buf_next = tmp;}
                    // read ahead (in next)
                    read_next = ins.read(buf_next);
                    _eof = read_next == -1;
                }
                if (!_eof) {
                    trans.update(buf, 0, read);
                    trans.update(buf_next, 0, read_next);
                }
            }
            if (_eof) {
                assert !eof;
                trans.updateFinal(buf, 0, read);
            }
        }
        else
            if (!eof) { // eof global flag also used as finalisation flag
                        // (updateFinal() must be called only once -- the first
                        // time EOF is met).
                eof = true;
                trans.updateFinal(buf, 0, 0);
            }
    }
    private static final Logger LOG = Logger.getLogger(ByteArrayTransformingInputStream.class.getName());
}
