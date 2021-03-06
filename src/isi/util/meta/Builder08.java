package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder08<T, T00, T01, T02, T03, T04, T05, T06, T07, T08> {

	///////////////////////////////////////////////////////
	// state
	private final Class<? extends T> type;
	private final Class<? extends T00> type00;
	private final Class<? extends T01> type01;
	private final Class<? extends T02> type02;
	private final Class<? extends T03> type03;
	private final Class<? extends T04> type04;
	private final Class<? extends T05> type05;
	private final Class<? extends T06> type06;
	private final Class<? extends T07> type07;
	private final Class<? extends T08> type08;
	private T00 v00;
	private T01 v01;
	private T02 v02;
	private T03 v03;
	private T04 v04;
	private T05 v05;
	private T06 v06;
	private T07 v07;
	private T08 v08;
	private boolean set00 = false;
	private boolean set01 = false;
	private boolean set02 = false;
	private boolean set03 = false;
	private boolean set04 = false;
	private boolean set05 = false;
	private boolean set06 = false;
	private boolean set07 = false;
	private boolean set08 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder08 (
			final Class<? extends T> type,
			final Class<? extends T00> type00,
			final Class<? extends T01> type01,
			final Class<? extends T02> type02,
			final Class<? extends T03> type03,
			final Class<? extends T04> type04,
			final Class<? extends T05> type05,
			final Class<? extends T06> type06,
			final Class<? extends T07> type07,
			final Class<? extends T08> type08) {
		this.type = type;
		this.type00 = type00;
		this.type01 = type01;
		this.type02 = type02;
		this.type03 = type03;
		this.type04 = type04;
		this.type05 = type05;
		this.type06 = type06;
		this.type07 = type07;
		this.type08 = type08;
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
		assert set00 && set01 && set02 && set03 && set04 && set05 && set06 && set07 && set08;
		return type
				.getConstructor(type00, type01, type02, type03, type04, type05, type06, type07, type08)
				.newInstance(v00, v01, v02, v03, v04, v05, v06, v07, v08);
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

	///////////////////////////////////////////////////////
	// v05
	public void Set05 (final T05 v05) {
		assert !set05;
		this.v05 = v05;
		set05 = true;
	}

	public T05 Get05 () {
		assert set05;
		return v05;
	}

	public boolean IsSet05 () {
		return set05;
	}

	///////////////////////////////////////////////////////
	// v06
	public void Set06 (final T06 v06) {
		assert !set06;
		this.v06 = v06;
		set06 = true;
	}

	public T06 Get06 () {
		assert set06;
		return v06;
	}

	public boolean IsSet06 () {
		return set06;
	}

	///////////////////////////////////////////////////////
	// v07
	public void Set07 (final T07 v07) {
		assert !set07;
		this.v07 = v07;
		set07 = true;
	}

	public T07 Get07 () {
		assert set07;
		return v07;
	}

	public boolean IsSet07 () {
		return set07;
	}

	///////////////////////////////////////////////////////
	// v08
	public void Set08 (final T08 v08) {
		assert !set08;
		this.v08 = v08;
		set08 = true;
	}

	public T08 Get08 () {
		assert set08;
		return v08;
	}

	public boolean IsSet08 () {
		return set08;
	}
}
