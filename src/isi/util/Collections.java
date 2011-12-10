package isi.util;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
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
    public static <T> T find(final Collection<T> col, final Predicate<? super T> pred) {
        for (final T obj : col)
            if (pred.accept(obj))
                return obj;
        return null;
    }
    public static <T> boolean find_if(final Collection<T> col, final Predicate<T> pred) {
        for (final T obj : col)
            if (pred.accept(obj))
                return true;
        return false;
    }

    public static <T> void writeAsStringTo(final Iterator<T> ite, final PrintStream out) {
        out.print('[');
        if (ite.hasNext())
            out.print(ite.next());
        while (ite.hasNext()) {
            out.print(", ");
            out.print(ite.next());
        }
        out.print(']');
    }

    private Collections() {
    }
    private static final Logger LOG = Logger.getLogger(Collections.class.getName());
}
