package isi.util;

import java.util.logging.Logger;

/**
 *
 * @param <T1>
 * @param <T2>
 * @author TURBO_X
 */
public class Tuple2<T1, T2> {
    private T1 o1;
    private T2 o2;
    public Tuple2(final T1 _o1, final T2 _o2) {
        o1 = _o1;
        o2 = _o2;
    }
    public Tuple2() {
    }

    public T1 get1() {
        return o1;
    }

    public void set1(T1 o1) {
        this.o1 = o1;
    }

    public T2 get2() {
        return o2;
    }

    public void set2(T2 o2) {
        this.o2 = o2;
    }
    private static final Logger LOG = Logger.getLogger(Tuple2.class.getName());

}
