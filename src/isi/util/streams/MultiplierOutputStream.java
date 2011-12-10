package isi.util.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author rudolf
 */
public class MultiplierOutputStream extends OutputStream {

    private final Collection<OutputStream> outs;
    public MultiplierOutputStream(final Collection<OutputStream> _outs) {
        outs = new LinkedList<>(_outs);
    }

    public MultiplierOutputStream(final OutputStream[] _outs) {
        outs = java.util.Arrays.asList(_outs);
    }

    @Override
    public void close() throws IOException {
        for (OutputStream out : outs)
            out.close();
    }

    @Override
    public void flush() throws IOException {
        for (OutputStream out : outs)
            out.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (OutputStream out : outs)
            out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (OutputStream out : outs)
            out.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        for (OutputStream out : outs)
            out.write(b);
    }
    private static final Logger LOG = Logger.getLogger(MultiplierOutputStream.class.getName());

}
