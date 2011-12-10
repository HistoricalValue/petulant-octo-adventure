package isi.util;

import isi.util.reflect.ValueToClassMapper;
import java.util.logging.Logger;

/**
 * @date 2010-06-09 13.40
 * @author TURBO_X
 */
public class Reflection {

    private Reflection(){}

    public static <T> T[] newarray(Class<T> type, int length) {
        @SuppressWarnings("unchecked")
        final T[] result = ((T[]) java.lang.reflect.Array.newInstance(type, length));
        return result;
    }

    private static final ValueToClassMapper<Object> valueToClassMapper = new ValueToClassMapper<>();
    public static Class<?>[] mapObjectsToTypes(Object... objs) {
        return isi.util.Functional.mapArray(
                objs,
                valueToClassMapper,
                Class.class
        );
    }
    private static final Logger LOG = Logger.getLogger(Reflection.class.getName());
}
