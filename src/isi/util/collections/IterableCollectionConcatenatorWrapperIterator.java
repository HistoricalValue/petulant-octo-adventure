package isi.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

public class IterableCollectionConcatenatorWrapperIterator<T> implements Iterator<T> {

    // state
    private final Iterator<? extends Iterable<? extends T>> iterables;
    //
    public IterableCollectionConcatenatorWrapperIterator(final Collection<? extends Iterable<? extends T>> _iterables) {
        iterables = _iterables.iterator();
    }
    
    @Override
    @SuppressWarnings("NestedAssignment")
    public boolean hasNext() {
        boolean hasNext = false;
        Iterator<? extends T> ite = _getCurrentIterator();
        while (!hasNext && ite != null)
            if (!(hasNext = ite.hasNext()))
                ite = _advanceIterator();
        return hasNext;
    }

    @Override
    public T next() {
        final Iterator<? extends T> ite = _getCurrentIterator();
        return ite != null? ite.next() : null;
    }

    @Override
    public void remove() {
        final Iterator<? extends T> ite = _getCurrentIterator();
        if (ite != null)
            ite.remove();
    }


    ///internal iterator state ///////////////////////////////////
    private Iterator<? extends T> _currentIterator;
    private Iterator<? extends T> _advanceIterator() {
        _currentIterator=null;
        return _getCurrentIterator();
    }
    private Iterator<? extends T> _getCurrentIterator() {
        if (_currentIterator == null && iterables.hasNext())
            _currentIterator = iterables.next().iterator();
        return _currentIterator;
    }
    private static final Logger LOG = Logger.getLogger(IterableCollectionConcatenatorWrapperIterator.class.getName());
}
