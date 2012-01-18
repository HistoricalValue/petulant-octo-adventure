package isi.util.streams;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class InitialisableInputStreamSequenceInputStream extends InitialisableInputStream {

    private final List<InitialisableInputStream> input_streams;
    private final Iterator<InitialisableInputStream> ite;
    private InitialisableInputStream current_ins;
    private boolean closed;
    private boolean eof;

    public InitialisableInputStreamSequenceInputStream(List<InitialisableInputStream> _input_streams) {
        input_streams = new LinkedList<>(_input_streams);
        ite = input_streams.iterator();
        current_ins = null;
        closed = false;
        eof = false;
    }

    @Override
    public int available() throws IOException {
        p_prepare_stream();
        return eof ? 0 : current_ins.available();
    }

    @Override
    public void close() throws IOException {
        p_prepare_stream();
        if (current_ins != null)
            current_ins.close();
        closed = true;
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read(final byte[] b) throws IOException {
        return p_sequential_read(new readop() {
            @Override
            public int readon(InitialisableInputStream iins) throws IOException {
                return iins.read(b);
            }
        });
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        return p_sequential_read(new readop() {
            @Override
            public int readon(InitialisableInputStream iins) throws IOException {
                return iins.read(b, off, len);
            }
        });
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("not supported");
    }

    @Override
    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    @Override
    public int read() throws IOException {
        return p_sequential_read(new readop() {
            @Override
            public int readon(InitialisableInputStream iins) throws IOException {
                return iins.read();
            }
        });
    }

    private void p_prepare_stream() throws IOException {
        if (closed)
            throw new IOException("closed stream");
        if (current_ins == null) { // need to advance current_ins
            if (ite.hasNext()) {
                current_ins = ite.next();
                if (! current_ins.initialise())
                    throw new IOException("could not initialise stream " + current_ins);
            }
            else
                eof = true;
        }
    }
    private void p_postpare_stream() throws IOException {
        current_ins.cleanup();
        current_ins = null;
    }

    @Override
    public boolean initialise () throws IOException {
        return true;
    }

    @Override
    public void cleanup () throws IOException {
    }

    private interface readop { int readon(InitialisableInputStream iins) throws IOException; }
    @SuppressWarnings("NestedAssignment")
    private int p_sequential_read(final readop rop) throws IOException {
        p_prepare_stream();
        int current_read = -1;
        while (!eof && (current_read = rop.readon(current_ins)) == -1) {
            p_postpare_stream();
            p_prepare_stream();
        }
        return eof ? -1 : current_read;
    }
    private static final Logger LOG = Logger.getLogger(InitialisableInputStreamSequenceInputStream.class.getName());
}
