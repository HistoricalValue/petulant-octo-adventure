package isi.util;

import java.util.Comparator;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class Arrays {

    private static interface array_comparator {
        int len(final Object a);
        int compare(Object a1, int i1, Object a2, int i2);
    }
    private static int cmp_impl(final Object a1, final int off1, final Object a2, final int off2, final int len, final array_comparator c) {
        if (off1 >= c.len(a1)) throw new IndexOutOfBoundsException("off1 >= a1.length");
        if (off2 >= c.len(a2)) throw new IndexOutOfBoundsException("off2 >= a2.length");
        if (off1 + len > c.len(a1)) throw new IndexOutOfBoundsException("off1 + len > a1.length");
        if (off2 + len > c.len(a2)) throw new IndexOutOfBoundsException("off2 + len > a2.length");
        if (off1 < 0) throw new IndexOutOfBoundsException("off1 < 0");
        if (off2 < 0) throw new IndexOutOfBoundsException("off2 < 0");
        if (len  < 0) throw new IndexOutOfBoundsException("len  < 0");
        final int l1 = off1 + len, l2 = off2 + len;
        boolean done = false;
        int result = 0;
        for (int i = 0; i < len && !done; ++i) {
            result = c.compare(a1, off1 + i, a2, off2 + i);
            done = result != 0;
        }
        if (! done) {
            assert result == 0;
            result = Integer.valueOf(l1).compareTo(l2);
        }
        return result;
    }

    public static <T> int cmp(final T[] a1, final int off1, final T[] a2, final int off2, final int len, final Comparator<T> c) {
        return cmp_impl(a1, off1, a2, off2, len, new array_comparator() {
            @Override public int len(Object a) {
                @SuppressWarnings("unchecked")
                final T[] ta = (T[]) a;
                return (ta).length;
            }
            @Override public int compare(Object a1, int i1, Object a2, int i2) {
                @SuppressWarnings("unchecked")
                final T[] ta1 = (T[]) a1, ta2 = (T[]) a2;
                return c.compare((ta1)[i1], (ta2)[i2]);
            }
        });
    }
    public static int cmp(final byte[] a1, final int off1, final byte[] a2, final int off2, final int len) {
        return cmp_impl(a1, off1, a2, off2, len, new array_comparator() {
            @Override public int len(Object a) { return ((byte[])a).length; }
            @Override public int compare(Object a1, int i1, Object a2, int i2) {
                return Math.cmp(((byte[])a1)[i1], ((byte[])a2)[i2]); }
        });
    }

    public static Byte[] objectify(byte[] bs) {
        final Byte[] result = new Byte[bs.length];
        for (int i = 0; i < result.length; ++i)
            result[i] = bs[i];
        return result;
    }

    public static byte[] makeByteArray(final int... bytes) throws IllegalArgumentException {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            final int b = bytes[i];
            if ((b & 0xff) != b)
                throw new IllegalArgumentException("Not a byte: " + b);
            result[i] = (byte)b;
        }
        return result;
    }


    private static final Logger LOG = Logger.getLogger(Arrays.class.getName());

    private Arrays () {
    }
}
