package isi.util;

import isi.util.collections.ExternalIteratorIterable;
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
	
	///////////////////////////////////////////////////////
	//
	private Iterators () {
	}
	
}
