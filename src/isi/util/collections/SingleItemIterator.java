package isi.util.collections;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * @param <T>
 * @date 2010-06-09 04.19am
 * @author TURBO_X
 */
public class SingleItemIterator<T> implements Iterator<T> {

    private final T obj;
    public SingleItemIterator(final T _obj) {
        obj = _obj;
    }
    public static <T> SingleItemIterator<T> create(final T obj) {
        return new SingleItemIterator<>(obj);
    }

    private boolean object_offered = false;
    @Override
    public boolean hasNext() {
        return !object_offered;
    }

    @Override
    public T next() {
        T result = null;
        if (hasNext()) {
            object_offered = true;
            result = obj;
        }
        return result;
    }

    @Override
    public void remove() {
    }
    private static final Logger LOG = Logger.getLogger(SingleItemIterator.class.getName());

}
