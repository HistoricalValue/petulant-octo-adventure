package isi.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stringifier {

	///////////////////////////////////////////////////////
	// static utils
	private static final int DefaultBobCapacity = 1 << 10;

	///////////////////////////////////////////////////////
	//
	public String ToString (final Object[] arr) {
		return new StringBuilder(DefaultBobCapacity).append("[").append(Strings.Join(arr, ", ", this)).append("]").toString();
	}

	public String ToString (final Map<?, ?> map) {
		return new StringBuilder(DefaultBobCapacity).append("{m").append(ToString(map.entrySet())).append("}").toString();
	}

	public String ToString (final Map.Entry<?, ?> entry) {
		return new StringBuilder(DefaultBobCapacity).append("{e").append(ToString(entry.getKey())).append(" => ").append(ToString(entry.getValue())).append("}").toString();
	}

	public String ToString (final Set<?> set) {
		return new StringBuilder(DefaultBobCapacity).append("{s").append(Strings.Join(set, ", ", this)).append("}").toString();
	}

	public String ToString (final List<?> list) {
		return new StringBuilder(DefaultBobCapacity).append("[l").append(Strings.Join(list, ", ", this)).append("]").toString();
	}

	public String ToString (final String string) {
		return string;
	}

	private String ToString (final Class<?> klass) {
		final StringBuilder bob = new StringBuilder(DefaultBobCapacity);
		final Class<?> parent = klass.getSuperclass();
		final Class<?>[] interfaces = klass.getInterfaces();

		bob.append("(Class:").append(klass.getCanonicalName());

		if (interfaces.length > 0)
				bob.append("+").append(ToString(interfaces));
		if (parent != null)
			bob.append("<").append(ToString(parent));

		return bob.append(")").toString();
	}

	public String ToString (final Object o) {
		String result;

		if (o == null)
			result = "null";
		else
			if (o.getClass().isArray())
				result = ToString((Object[]) o);
			else
			if (Map.class.isAssignableFrom(o.getClass()))
				result = ToString((Map<?, ?>) o);
			else
			if (Map.Entry.class.isAssignableFrom(o.getClass()))
				result = ToString((Map.Entry<?, ?>) o);
			else
			if (Set.class.isAssignableFrom(o.getClass()))
				result = ToString((Set<?>) o);
			else
			if (List.class.isAssignableFrom(o.getClass()))
				result = ToString((List<?>) o);
			else
			if (String.class.isAssignableFrom(o.getClass()))
				result = ToString((String) o);
			else
			if (Class.class.isAssignableFrom(o.getClass()))
				result = ToString((Class<?>) o);
			else
				throw new UnsupportedOperationException(ToString(o.getClass()));

		return result;
	}
}
