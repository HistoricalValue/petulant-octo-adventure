package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder01<T, T00, T01> {

	///////////////////////////////////////////////////////
	// state
	private final Class<? extends T> type;
	private final Class<? extends T00> type00;
	private final Class<? extends T01> type01;
	private T00 v00;
	private T01 v01;
	private boolean set00 = false;
	private boolean set01 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder01 (
			final Class<? extends T> type,
			final Class<? extends T00> type00,
			final Class<? extends T01> type01) {
		this.type = type;
		this.type00 = type00;
		this.type01 = type01;
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
		assert set00 && set01;
		return type
				.getConstructor(type00, type01)
				.newInstance(v00, v01);
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

	///////////////////////////////////////////////////////
	// v01
	public void Set01 (final T01 v01) {
		assert !set01;
		this.v01 = v01;
		set01 = true;
	}
	
	public T01 Get01 () {
		assert set01;
		return v01;
	}
	
	public boolean IsSet01 () {
		return set01;
	}
}
