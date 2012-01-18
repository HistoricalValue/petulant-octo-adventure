package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder04<T, T00, T01, T02, T03, T04> {

	///////////////////////////////////////////////////////
	// state
	private final Class<? extends T> type;
	private final Class<? extends T00> type00;
	private final Class<? extends T01> type01;
	private final Class<? extends T02> type02;
	private final Class<? extends T03> type03;
	private final Class<? extends T04> type04;
	private T00 v00;
	private T01 v01;
	private T02 v02;
	private T03 v03;
	private T04 v04;
	private boolean set00 = false;
	private boolean set01 = false;
	private boolean set02 = false;
	private boolean set03 = false;
	private boolean set04 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder04 (
			final Class<? extends T> type,
			final Class<? extends T00> type00,
			final Class<? extends T01> type01,
			final Class<? extends T02> type02,
			final Class<? extends T03> type03,
			final Class<? extends T04> type04) {
		this.type = type;
		this.type00 = type00;
		this.type01 = type01;
		this.type02 = type02;
		this.type03 = type03;
		this.type04 = type04;
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
		assert set00 && set01 && set02 && set03 && set04;
		return type
				.getConstructor(type00, type01, type02, type03, type04)
				.newInstance(v00, v01, v02, v03, v04);
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

	///////////////////////////////////////////////////////
	// v03
	public void Set03 (final T03 v03) {
		assert !set03;
		this.v03 = v03;
		set03 = true;
	}

	public T03 Get03 () {
		assert set03;
		return v03;
	}

	public boolean IsSet03 () {
		return set03;
	}

	///////////////////////////////////////////////////////
	// v04
	public void Set04 (final T04 v04) {
		assert !set04;
		this.v04 = v04;
		set04 = true;
	}

	public T04 Get04 () {
		assert set04;
		return v04;
	}

	public boolean IsSet04 () {
		return set04;
	}
}
