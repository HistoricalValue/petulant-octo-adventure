package isi.util.reflect;

import java.util.logging.Logger;

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
    private static final Logger LOG = Logger.getLogger(ValueToClassMapper.class.getName());

}
