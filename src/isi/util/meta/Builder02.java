package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder02<T, T00, T01, T02> {

	///////////////////////////////////////////////////////
	// state
	private final Class<? extends T> type;
	private final Class<? extends T00> type00;
	private final Class<? extends T01> type01;
	private final Class<? extends T02> type02;
	private T00 v00;
	private T01 v01;
	private T02 v02;
	private boolean set00 = false;
	private boolean set01 = false;
	private boolean set02 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder02 (
			final Class<? extends T> type,
			final Class<? extends T00> type00,
			final Class<? extends T01> type01,
			final Class<? extends T02> type02) {
		this.type = type;
		this.type00 = type00;
		this.type01 = type01;
		this.type02 = type02;
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
		assert set00 && set01 && set02;
		return type
				.getConstructor(type00, type01, type02)
				.newInstance(v00, v01, v02);
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

	///////////////////////////////////////////////////////
	// v02
	public void Set02 (final T02 v02) {
		assert !set02;
		this.v02 = v02;
		set02 = true;
	}
	
	public T02 Get02 () {
		assert set02;
		return v02;
	}
	
	public boolean IsSet02 () {
		return set02;
	}
}
