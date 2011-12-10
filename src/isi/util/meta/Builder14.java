package isi.util.meta;

import java.lang.reflect.InvocationTargetException;

public class Builder14<T, T00, T01, T02, T03, T04, T05, T06, T07, T08, T09, T10, T11, T12, T13, T14> {

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
	private final Class<? extends T09> type09;
	private final Class<? extends T10> type10;
	private final Class<? extends T11> type11;
	private final Class<? extends T12> type12;
	private final Class<? extends T13> type13;
	private final Class<? extends T14> type14;
	private T00 v00;
	private T01 v01;
	private T02 v02;
	private T03 v03;
	private T04 v04;
	private T05 v05;
	private T06 v06;
	private T07 v07;
	private T08 v08;
	private T09 v09;
	private T10 v10;
	private T11 v11;
	private T12 v12;
	private T13 v13;
	private T14 v14;
	private boolean set00 = false;
	private boolean set01 = false;
	private boolean set02 = false;
	private boolean set03 = false;
	private boolean set04 = false;
	private boolean set05 = false;
	private boolean set06 = false;
	private boolean set07 = false;
	private boolean set08 = false;
	private boolean set09 = false;
	private boolean set10 = false;
	private boolean set11 = false;
	private boolean set12 = false;
	private boolean set13 = false;
	private boolean set14 = false;

	///////////////////////////////////////////////////////
	// construction
	public Builder14 (
			final Class<? extends T> type,
			final Class<? extends T00> type00,
			final Class<? extends T01> type01,
			final Class<? extends T02> type02,
			final Class<? extends T03> type03,
			final Class<? extends T04> type04,
			final Class<? extends T05> type05,
			final Class<? extends T06> type06,
			final Class<? extends T07> type07,
			final Class<? extends T08> type08,
			final Class<? extends T09> type09,
			final Class<? extends T10> type10,
			final Class<? extends T11> type11,
			final Class<? extends T12> type12,
			final Class<? extends T13> type13,
			final Class<? extends T14> type14) {
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
		this.type09 = type09;
		this.type10 = type10;
		this.type11 = type11;
		this.type12 = type12;
		this.type13 = type13;
		this.type14 = type14;
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
		assert set00 && set01 && set02 && set03 && set04 && set05 && set06 && set07 && set08 && set09 && set10 && set11 && set12 && set13 && set14;
		return type
				.getConstructor(type00, type01, type02, type03, type04, type05, type06, type07, type08, type09, type10, type11, type12, type13, type14)
				.newInstance(v00, v01, v02, v03, v04, v05, v06, v07, v08, v09, v10, v11, v12, v13, v14);
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

	///////////////////////////////////////////////////////
	// v09
	public void Set09 (final T09 v09) {
		assert !set09;
		this.v09 = v09;
		set09 = true;
	}
	
	public T09 Get09 () {
		assert set09;
		return v09;
	}
	
	public boolean IsSet09 () {
		return set09;
	}

	///////////////////////////////////////////////////////
	// v10
	public void Set10 (final T10 v10) {
		assert !set10;
		this.v10 = v10;
		set10 = true;
	}
	
	public T10 Get10 () {
		assert set10;
		return v10;
	}
	
	public boolean IsSet10 () {
		return set10;
	}

	///////////////////////////////////////////////////////
	// v11
	public void Set11 (final T11 v11) {
		assert !set11;
		this.v11 = v11;
		set11 = true;
	}
	
	public T11 Get11 () {
		assert set11;
		return v11;
	}
	
	public boolean IsSet11 () {
		return set11;
	}

	///////////////////////////////////////////////////////
	// v12
	public void Set12 (final T12 v12) {
		assert !set12;
		this.v12 = v12;
		set12 = true;
	}
	
	public T12 Get12 () {
		assert set12;
		return v12;
	}
	
	public boolean IsSet12 () {
		return set12;
	}

	///////////////////////////////////////////////////////
	// v13
	public void Set13 (final T13 v13) {
		assert !set13;
		this.v13 = v13;
		set13 = true;
	}
	
	public T13 Get13 () {
		assert set13;
		return v13;
	}
	
	public boolean IsSet13 () {
		return set13;
	}

	///////////////////////////////////////////////////////
	// v14
	public void Set14 (final T14 v14) {
		assert !set14;
		this.v14 = v14;
		set14 = true;
	}
	
	public T14 Get14 () {
		assert set14;
		return v14;
	}
	
	public boolean IsSet14 () {
		return set14;
	}
}
