package isi.util;

import isi.util.collections.ExternalIteratorIterable;
import isi.util.collections.IterableConcatenationIterator;
import isi.util.collections.SingleItemIterable;
import java.util.Iterator;

/**
 *
 * @author TURBO_X
 */
public class Iterators {
	
	///////////////////////////////////////////////////////
	//
	public static <T> Iterable<T> SingleItem (final T item) {
		return new SingleItemIterable<>(item);
	}
	
	public static <T> Iterable<T> External (final Iterator<T> iter) {
		return new ExternalIteratorIterable<>(iter);
	}
	
	public static <T> Iterable<T> Concatenate (final Iterable<? extends T> i1, final Iterable<? extends T> i2) {
		return External(IterableConcatenationIterator.create(i1, i2));
	}
	public static <T> Iterable<T> Concatenate (final Iterable<? extends T> iterable, final T element) {
		return Concatenate(iterable, SingleItem(element));
	}
	
	///////////////////////////////////////////////////////
	//
	private Iterators () {
	}
	
}
