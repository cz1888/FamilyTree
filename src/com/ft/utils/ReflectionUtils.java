package com.ft.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 鍙嶅皠宸ュ叿绫�
 * <p/>
 *  鎻愪緵璋冪敤getter/setter鏂规硶, 璁块棶绉佹湁鍙橀噺, 璋冪敤绉佹湁鏂规硶, 鑾峰彇娉涘瀷绫诲瀷Class, 琚獳OP杩囩殑鐪熷疄绫荤瓑宸ュ叿鍑芥暟.
 * 
 * @create date: 2013-12-29
 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * 璋冪敤Getter鏂规硶.
	 * 
	 * @param target 璋冪敤瀵硅薄
	 * @param propertyName 灞炴�鍚嶇О
	 * @return
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 璋冪敤Setter鏂规硶.浣跨敤value鐨凜lass鏉ユ煡鎵維etter鏂规硶.
	 * 
	 * @param target
	 * @param propertyName
	 * @param value
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value) {
		invokeSetterMethod(target, propertyName, value, null);
	}

	/**
	 * 璋冪敤Setter鏂规硶.
	 * 
	 * @param target
	 * @param propertyName
	 * @param value
	 * @param propertyType 鐢ㄤ簬鏌ユ壘Setter鏂规硶,涓虹┖鏃朵娇鐢╲alue鐨凜lass鏇夸唬.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 鐩存帴璇诲彇瀵硅薄灞炴�鍊� 鏃犺private/protected淇グ绗� 涓嶇粡杩噂etter鍑芥暟.
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 鐩存帴璁剧疆瀵硅薄灞炴�鍊� 鏃犺private/protected淇グ绗� 涓嶇粡杩噑etter鍑芥暟.
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父:{}", e.getMessage());
		}
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredField,骞跺己鍒惰缃负鍙闂�
	 * <p/>
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒� 杩斿洖null.
	 */
	public static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, "object can't be null");
		Assert.hasText(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field涓嶅湪褰撳墠绫诲畾涔�缁х画鍚戜笂杞瀷
			}
		}
		return null;
	}
	
	/**
	 * 寮鸿璁剧疆Field鍙闂�鏀瑰彉private/protected鐨勬垚鍛樺彉閲忎负public锛屽敖閲忎笉璋冪敤瀹為檯鏀瑰姩鐨勮鍙ワ紝閬垮厤JDK鐨凷ecurityManager鎶辨�銆�
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	/**
	 * 鏀瑰彉private/protected鐨勬柟娉曚负public锛屽敖閲忎笉璋冪敤瀹為檯鏀瑰姩鐨勮鍙ワ紝閬垮厤JDK鐨凷ecurityManager鎶辨�銆�
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}
	
	/**
	 * 鐩存帴璋冪敤瀵硅薄鏂规硶, 鏃犺private/protected淇グ绗�
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Assert.notNull(object, "object can't be null");
		Assert.hasText(methodName, "methodName can't be blank");

		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredMethod.
	 * <p/>
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒� 杩斿洖null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {// NOSONAR
				// Method涓嶅湪褰撳墠绫诲畾涔�缁х画鍚戜笂杞瀷
			}
		}
		return null;
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱Class瀹氫箟涓０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲瀷. 濡傛棤娉曟壘鍒� 杩斿洖Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱瀹氫箟Class鏃跺０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲瀷. 濡傛棤娉曟壘鍒� 杩斿洖Object.class. 
	 * 濡俻ublic UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	
	/**
	 * 鑾峰彇琚獳OP杩囩殑鍘熷绫�
	 * @param instance
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class<?> getRealClass(Object instance) {
		Assert.notNull(instance, "Instance must not be null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains("$$")) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * 灏嗗弽灏勬椂鐨刢hecked exception杞崲涓簎nchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	
}
