package isi.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
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
	public static <T> ArrayList<T> newArrayList (final Iterable<? extends T> i, final int length) {
		final ArrayList<T> result = new ArrayList<>(length);
		for (final T o: i)
			result.add(o);
		return result;
	}
	
	///////////////////////////////////////////////////////
	public static <T> List<T> unmodifiableList (final Iterable<? extends T> i, final int length) {
		return java.util.Collections.unmodifiableList(newArrayList(i, length));
	}
	
	public static <T> List<T> unmodifiableList (final Iterable<? extends T> i) {
		final List<T> l = new LinkedList<>();
		for (final T o: i)
			l.add(o);
		return unmodifiableList(l, l.size());
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
