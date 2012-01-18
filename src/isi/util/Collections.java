package isi.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class Collections {
    public static <T> T addAndReturn(final Collection<T> col, final T obj) {
        col.add(obj);
        return obj;
    }
    public static <T> T find(final Iterable<? extends T> col, final Predicate<? super T> pred) {
        for (final T obj : col)
            if (pred.accept(obj))
                return obj;
        return null;
    }
    public static <T> boolean find_if(final Iterable<? extends T> col, final Predicate<? super T> pred) {
        for (final T obj : col)
            if (pred.accept(obj))
                return true;
        return false;
    }

    public static <T> void writeAsStringTo(final Iterator<? extends T> ite, final PrintStream out) {
        out.print('[');
        if (ite.hasNext())
            out.print(ite.next());
        while (ite.hasNext()) {
            out.print(", ");
            out.print(ite.next());
        }
        out.print(']');
    }

	///////////////////////////////////////////////////////
	public static <T> int size (final Iterable<? extends T> i) {
		int size = 0;
		for (final T o: i)
			++size;
		return size;
	}

	///////////////////////////////////////////////////////
	public static <T> ArrayList<T> newArrayList (final Iterable<? extends T> i, final int lengthHint) {
		final ArrayList<T> result = new ArrayList<>(lengthHint);
		for (final T o: i)
			result.add(o);
		return result;
	}

	public static <T> LinkedList<T> newLinkedList (final Iterable<? extends T> i) {
		final LinkedList<T> result = new LinkedList<>();
		for (final T o: i)
			result.add(o);
		return result;
	}

	///////////////////////////////////////////////////////
	public static <T> List<T> newUnmodifiableList (final Iterable<? extends T> i, final int lengthHint) {
		return java.util.Collections.unmodifiableList(newArrayList(i, lengthHint));
	}

	public static <T> List<T> newUnmodifiableList (final Iterable<? extends T> i) {
		return java.util.Collections.unmodifiableList(newLinkedList(i));
	}

	public static <T> List<T> newUnmodifiableList (final Collection<T> col) {
		return newUnmodifiableList(col, col.size());
	}

	public static <T> Deque<T> newUnmodifiableDeque (final Iterable<? extends T> i) {
		return new Deque<T> () {
			private final LinkedList<T> list = newLinkedList(i);
			private final List<T> unmod = java.util.Collections.unmodifiableList(list);

			@Override
			public void addFirst (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public void addLast (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean offerFirst (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean offerLast (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T removeFirst () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T removeLast () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T pollFirst () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T pollLast () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T getFirst () {
				return list.getFirst();
			}

			@Override
			public T getLast () {
				return list.getLast();
			}

			@Override
			public T peekFirst () {
				return list.peekFirst();
			}

			@Override
			public T peekLast () {
				return list.peekLast();
			}

			@Override
			public boolean removeFirstOccurrence (final Object o) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean removeLastOccurrence (final Object o) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean add (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean offer (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T remove () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T poll () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T element () {
				return list.element();
			}

			@Override
			public T peek () {
				return list.peek();
			}

			@Override
			public void push (final T e) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public T pop () {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean remove (final Object o) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean contains (final Object o) {
				return unmod.contains(o);
			}

			@Override
			public int size () {
				return unmod.size();
			}

			@Override
			public Iterator<T> iterator () {
				return unmod.iterator();
			}

			@Override
			public Iterator<T> descendingIterator () {
				return new Iterator<T> () {
					final Iterator<T> ite = list.descendingIterator();

					@Override
					public boolean hasNext () {
						return ite.hasNext();
					}

					@Override
					public T next () {
						return ite.next();
					}

					@Override
					public void remove () {
						throw new UnsupportedOperationException("Not permited.");
					}
				};
			}

			@Override
			public boolean isEmpty () {
				return unmod.isEmpty();
			}

			@Override
			public Object[] toArray () {
				return unmod.toArray();
			}

			@Override
			public <T> T[] toArray (final T[] a) {
				return unmod.toArray(a);
			}

			@Override
			public boolean containsAll (final Collection<?> c) {
				return unmod.containsAll(c);
			}

			@Override
			public boolean addAll (final Collection<? extends T> c) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean removeAll (final Collection<?> c) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public boolean retainAll (final Collection<?> c) {
				throw new UnsupportedOperationException("Not permited.");
			}

			@Override
			public void clear () {
				throw new UnsupportedOperationException("Not permited.");
			}
		};
	}

	///////////////////////////////////////////////////////
	private static <F, T> Collection<? super T> map (final Iterable<? extends F> l, final Collection<? super T> into, final ValueMapper<? super F, ? extends T> m) {
		for (final F unmapped: l)
			into.add(m.map(unmapped));
		return into;
	}

	public static <F, T> LinkedList<T> map (final List<? extends F> l, final ValueMapper<? super F, ? extends T> m) {
		final LinkedList<T> mapped = new LinkedList<>();
		map(l, mapped, m);
		return mapped;
	}

	///////////////////////////////////////////////////////
	public static <T> LinkedList<T> select (final Iterable<? extends T> i, final Predicate<? super T> pred) {
		final LinkedList<T> result = new LinkedList<>();
		for (final T obj: i)
			if (pred.accept(obj))
				result.add(obj);
		return result;
	}

	public static <T> LinkedList<T> select (final Iterable<? extends T> i) {
		return select(i, Predicates.newNotNull());
	}

	///////////////////////////////////////////////////////

    private Collections() {
    }
    private static final Logger LOG = Logger.getLogger(Collections.class.getName());
}
