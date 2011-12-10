package isi.util.collections;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * @param <T>
 * @date 2010-06-09 4.22am
 * @author TURBO_X
 */
public class ExternalIteratorIterable<T> implements Iterable<T> {

    private final Iterator<T> ite;
    public ExternalIteratorIterable(final Iterator<T> _ite) {
        ite = _ite;
    }

    @Override
    public Iterator<T> iterator() {
        return ite;
    }
    private static final Logger LOG = Logger.getLogger(ExternalIteratorIterable.class.getName());

}
