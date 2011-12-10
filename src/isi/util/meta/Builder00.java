package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder00<T, T00> {

	///////////////////////////////////////////////////////
	// state
	private final Class<? extends T> type;
	private final Class<? extends T00> type00;
	private T00 v00;
	private boolean set00 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder00 (
			final Class<? extends T> type,
			final Class<? extends T00> type00) {
		this.type = type;
		this.type00 = type00;
	}
	
	///////////////////////////////////////////////////////
	//
	public T Build ()
		throws
			NoSuchMethodException,
			InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException
	{
		assert set00;
		return type
				.getConstructor(type00)
				.newInstance(v00);
	}

	///////////////////////////////////////////////////////
	// v00
	public void Set00 (final T00 v00) {
		assert !set00;
		this.v00 = v00;
		set00 = true;
	}
	
	public T00 Get00 () {
		assert set00;
		return v00;
	}
	
	public boolean IsSet00 () {
		return set00;
	}
}
