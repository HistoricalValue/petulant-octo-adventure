package isi.util;

public class OnceSettable<T> {
	private T val;
	private boolean set = false;

	@SuppressWarnings({"PublicInnerClass", "FinalClass"})
	public static final class GetUnsetOrSetSetException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	public void Set (final T val) throws GetUnsetOrSetSetException {
		if (set)
			throw new GetUnsetOrSetSetException();
		set = true;
		this.val = val;
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
