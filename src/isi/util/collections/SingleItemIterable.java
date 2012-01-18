package isi.util.collections;

import java.util.Iterator;

/**
 *
 * @author TURBO_X
 */
public class SingleItemIterable<T> implements Iterable<T> {

	private final T item;

	public SingleItemIterable (final T item) {
		this.item = item;
	}

	@Override
	public Iterator<T> iterator () {
		return new SingleItemIterator<>(item);
	}

}
