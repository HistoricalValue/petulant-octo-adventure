package isi.util;

public class OnceSettable<T> {
	private T val;
	private boolean set = false;

	@SuppressWarnings({"PublicInnerClass"})
	public static class GetUnsetOrSetSetException extends Exception {
		private static final long serialVersionUID = 1L;
	}
	
	@SuppressWarnings("PublicInnerClass")
	public static class GetUnsetOrSetSetRuntimeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		GetUnsetOrSetSetRuntimeException (final Throwable cause) {
			super(cause);
		}
	}

	public void Set (final T val) throws GetUnsetOrSetSetException {
		if (set)
			throw new GetUnsetOrSetSetException();
		set = true;
		this.val = val;
	}
	
	public void SetQuiet (final T val) throws GetUnsetOrSetSetRuntimeException {
		try {
			Set(val);
		} catch (final GetUnsetOrSetSetException ex) {
			throw new GetUnsetOrSetSetRuntimeException(ex);
		}
	}

	public void	Reset () throws GetUnsetOrSetSetException {
		if (!set)
			throw new GetUnsetOrSetSetException();
		set = false;
		this.val = null;
	}

	public T Get () throws GetUnsetOrSetSetException {
		if (!set)
			throw new GetUnsetOrSetSetException();
		return val;
	}

	public T GetIfSet () {
		return set? val : null;
	}

	public T UseUp () throws GetUnsetOrSetSetException {
		final T result = Get();
		Reset();
		return result;
	}

	public boolean IsSet () {
		return set;
	}
}
