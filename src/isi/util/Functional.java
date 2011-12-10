package isi.util;

import java.util.logging.Logger;

/**
 * @date 2010-06-09 12:53pm
 * @author TURBO_X
 */
public class Functional {
    private Functional(){}

    public static <FromType, ToType> ToType[] mapArray(
            final FromType[] values,
            final ValueMapper<? super FromType, ? extends ToType> mapper,
            final Class<ToType> totype
    ) {
        final ToType[] result = isi.util.Reflection.newarray(totype, values.length);
        for (int i = 0; i < result.length; ++i)
            result[i] = mapper.map(values[i]);
        return result;
    }
    private static final Logger LOG = Logger.getLogger(Functional.class.getName());
}
