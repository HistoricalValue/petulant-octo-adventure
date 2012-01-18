package isi.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @param <T>
 * @date 2010-06-06 12:40
 * @author TURBO_X
 * @deprecated "first must be refactored to work closely with IterableConcatenationIterator"
 */
@Deprecated
public class IteratorConcatenationIteratorProxy<T> implements Iterator<T> {

    // state
    private final Collection<Iterator<? extends T>> iterators;
    //
    /**
     * the given collection is used as-is, without copying.
     * @param _iterators
     */
    public IteratorConcatenationIteratorProxy(final Collection<Iterator<? extends T>> _iterators) {
        iterators = _iterators;
    }
    public IteratorConcatenationIteratorProxy() {
        this(new LinkedList<Iterator<? extends T>>());
    }
    //
    public static <T> IteratorConcatenationIteratorProxy<T> create(final Iterator<? extends T> ite) {
        final IteratorConcatenationIteratorProxy<T> result = new IteratorConcatenationIteratorProxy<>();
        return result.add(ite);
    }
    public static <T> IteratorConcatenationIteratorProxy<T> create(final Iterator<? extends T> ite1, final Iterator<? extends T> ite2) {
        final IteratorConcatenationIteratorProxy<T> result = new IteratorConcatenationIteratorProxy<>();
        return result.add(ite1).add(ite2);
    }
    public static <T> IteratorConcatenationIteratorProxy<T> create(final Iterator<? extends T>[] ites) {
        final IteratorConcatenationIteratorProxy<T> result = new IteratorConcatenationIteratorProxy<>();
        return result.add(java.util.Arrays.asList(ites));
    }
    /**
     * The given collection i added by the add() method.
     * @param <T>
     * @param ites
     * @return
     */
    public static <T> IteratorConcatenationIteratorProxy<T> createFromIteratorCollection(final Collection<? extends Iterator<? extends T>> ites) {
        final IteratorConcatenationIteratorProxy<T> result = new IteratorConcatenationIteratorProxy<>();
        return result.add(ites);
    }

    /**
     *
     * @param ite
     * @return this
     */
    public IteratorConcatenationIteratorProxy<T> add(final Iterator<? extends T> ite) {
        final boolean inserted_successfully = iterators.add(ite);
        assert inserted_successfully;
        return this;
    }
    /**
     * The given collection is addAll()-ed in the given collection (copied, in
     * other words, probably with O(n) cost as it's iterated over).
     * @param ites
     * @return this
     */
    public IteratorConcatenationIteratorProxy<T> add(final Collection<? extends Iterator<? extends T>> ites) {
        try {
            final boolean inserted_successfully = iterators.addAll(ites);
            assert inserted_successfully;
        } catch (final Exception ex) {
            assert false;
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        final Iterator<? extends T> curr = _getCurrentIterator();
        return curr != null && curr.hasNext() || false;
    }

    @Override
    public T next() {
        final Iterator<? extends T> curr = _getCurrentIterator();
        return curr != null? curr.next() : null;
    }

    @Override
    public void remove() {
        final Iterator<? extends T> curr = _getCurrentIterator();
        if (curr != null)
            curr.remove();
    }

    // internal iterating state and operations ///////////////////
    private Iterator<? extends T> _currentIterator;
    private Iterator<? extends T> _getCurrentIterator() {
        if (_currentIterator == null) {
            final Iterator<Iterator<? extends T>> iterators_iterator = iterators.iterator();
            if (iterators_iterator.hasNext())
                _currentIterator = iterators_iterator.next();
            // else currentIterator =(=) null
        }
        return _currentIterator;
    }
    private static final Logger LOG = Logger.getLogger(IteratorConcatenationIteratorProxy.class.getName());

}
