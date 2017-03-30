package tcs.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class Objects {

	/**
	 * method to get object parameter and return null value
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(final Object object) {
		return null == object;
	}

	/**
	 * method to return object value
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(final Object object) {
		return !Objects.isNull(object);
	}

	public static boolean isEmpty(final Object obj) {
		boolean equal = false;
		if (obj == null) {
			equal = true;
		} else {
			if (obj instanceof String) {
				equal = ((String) obj).trim().length() == 0;
			} else if (obj.getClass().isArray()) {
				equal = Array.getLength(obj) == 0;
			} else if (obj instanceof Map<?, ?>) {
				equal = ((Map<?, ?>) obj).isEmpty();
			} else if (obj instanceof Collection<?>) {
				equal = ((Collection<?>) obj).isEmpty();
			}
		}
		return equal;
	}

	public static boolean isNotEmpty(final Object obj) {
		return !isEmpty(obj);
	}

}