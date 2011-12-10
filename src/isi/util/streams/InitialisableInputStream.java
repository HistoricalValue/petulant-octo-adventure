package isi.util.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author TURBO_X
 */
public abstract class InitialisableInputStream extends InputStream {
    public abstract boolean initialise() throws IOException;
    public abstract void cleanup() throws IOException;
}
