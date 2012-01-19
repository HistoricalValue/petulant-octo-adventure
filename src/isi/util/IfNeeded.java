package isi.util;

import java.util.Objects;


public class IfNeeded<T> {

	///////////////////////////////////////////////////////
	// state
	private final Factory<? extends T> factory;
	private T inst;

	///////////////////////////////////////////////////////
	//
	public T Get () {
		if (inst == null)
			inst = factory.Create();
		return inst;
	}

	///////////////////////////////////////////////////////
	// constructors
	public IfNeeded (final Factory<? extends T> factory) {
		this.factory = Objects.requireNonNull(factory);
	}

	///////////////////////////////////////////////////////
	// utility factory methods
	public static <T> IfNeeded<T> Create (final Class<? extends T> klass)
			throws
				SecurityException,
				NoSuchMethodException {
		return new IfNeeded<>(Factories.DefaultFactory(klass));
	}

}
