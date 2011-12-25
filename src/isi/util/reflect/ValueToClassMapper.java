package isi.util.reflect;

/**
 * @param <T>
 * @date 2010-06-09 23:54pm
 * @author TURBO_X
 */
public class ValueToClassMapper<T> implements isi.util.ValueMapper<T, Class<?>> {

    @Override
    public Class<?> map(final T v) {
        return v.getClass();
    }

}
