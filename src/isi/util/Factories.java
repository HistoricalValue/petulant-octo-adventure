package isi.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Factories {

	///////////////////////////////////////////////////////
	//
	public static <T> Factory<T> DefaultFactory (final Constructor<? extends T> constructor, final Object... args) {
		return new Factory<T>() {
			@Override
			public T Create () throws FactoryException {
				try {
					return constructor.newInstance(args);
				} catch (
						InstantiationException |
						IllegalAccessException |
						IllegalArgumentException |
						InvocationTargetException ex) {
					throw new FactoryException(ex);
				}
			}
		};
	}

	public static <T> Factory<T> DefaultFactory (final Class<? extends T> klass, Class<?>... constructorParameterTypes)
			throws
				SecurityException,
				NoSuchMethodException {
		return DefaultFactory(klass.getConstructor(constructorParameterTypes));
	}

	///////////////////////////////////////////////////////
	// private
	private Factories () {
	}
}
