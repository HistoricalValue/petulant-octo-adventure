package isi.util.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class VoidOutputStream extends OutputStream {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public String toString() {
        return "VoidOutputStream";
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    }

    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VoidOutputStream other = (VoidOutputStream) obj;
        final boolean result = this == other;
        return result;
    }
    private static final Logger LOG = Logger.getLogger(VoidOutputStream.class.getName());

}
