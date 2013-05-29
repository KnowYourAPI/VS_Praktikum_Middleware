package mware_lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Skeleton {
	
	private static final Map<String, Class<?>> PRIMITIVE_TYPES;
	static {
		PRIMITIVE_TYPES = new HashMap<String, Class<?>>();
		PRIMITIVE_TYPES.put("int", int.class);
		PRIMITIVE_TYPES.put("long", long.class);
		PRIMITIVE_TYPES.put("double", double.class);
		PRIMITIVE_TYPES.put("float", float.class);
		PRIMITIVE_TYPES.put("boolean", boolean.class);
		PRIMITIVE_TYPES.put("char", char.class);
		PRIMITIVE_TYPES.put("byte", byte.class);
		PRIMITIVE_TYPES.put("void", void.class);
	    PRIMITIVE_TYPES.put("short", short.class);
	}
	
	private Object realObject;
	
	public Skeleton(Object realObject) {
		this.realObject = realObject;
	}
	
	public String invokeMethod(String rmiMessage) {

		// Message auseinanderbauen:
		
		String[] splittedMessage = rmiMessage.split("%"); 
		String methodName = splittedMessage[2];
		// Drei Abziehen wegen "INVOKE%<Objektname>%<Methodenname>% ..."
		int numberOfParameters = (splittedMessage.length - 3) / 2;
		String[] typeStrings = new String[numberOfParameters];
		String[] parameterStrings = new String[numberOfParameters];
		Class<?>[] parameterTypes = new Class<?>[numberOfParameters];
		Object[] parameters = new Object[numberOfParameters];

		if(splittedMessage.length > 3) {
			
			for(int i = 0; i < numberOfParameters; i ++) {
				typeStrings[i] = splittedMessage[(i*2)+3]; // i * 2, weil wir ueber die type, value-paare iterieren, 3er offset + 0 fuer Typ
				parameterStrings[i] = splittedMessage[(i*2)+4]; // i * 2, weil wir ueber die type, value-paare iterieren, 3er offset + 1 fuer Wert
			}
			
			for(int i= 0; i < numberOfParameters; i++) {
				parameterTypes[i] = getClassByName(typeStrings[i]);
			}
			
			// Parameterobjekte aus Strings erzeugen
			
			
			for(int i = 0; i < numberOfParameters; i++) {
				if(parameterTypes[i].isPrimitive()) {
					parameters[i] = instanciatePrimitive(parameterStrings[i], parameterTypes[i]);
				} else {
					Class<?> parameterClass = parameterTypes[i];
					Class<?> constructorParameterString = parameterStrings[i].getClass();
					Constructor<?> constructor;
					try {
						constructor = parameterClass.getConstructor(constructorParameterString);
						parameters[i] = constructor.newInstance(parameterStrings[i]);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// Methodenaufruf
		
		Class<?> realObjectClass = realObject.getClass();
		
		Method toBeInvoked = null;
		try {
			toBeInvoked = realObjectClass.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		Object returnValue = null;

		try {
			toBeInvoked.setAccessible(true);
			returnValue = toBeInvoked.invoke(realObject, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			//DIESE Exception wird geworfen, wenn die unterliegende Methode eine Exception wirft
			// Die tatsaechlich von der Methode geworfene Exception
			returnValue = e.getTargetException();
		}
		
		String returnMessage;
		if(returnValue instanceof Exception) {
			String errorType = returnValue.getClass().getName();
			String errorMessage = ((Throwable) returnValue).getMessage();
			returnMessage = "ERROR%" + errorType + "%" + errorMessage;
			return returnMessage;
		} else {
			if(returnValue == null) {
				return "NOERROR%" + void.class + "%" + null;
			} else {
				returnMessage = "RETURN%" + returnValue.getClass().getName() + "%" + returnValue;
				return returnMessage;	
			}
		}
	}
	
	private Object instanciatePrimitive(String primitiveAsString, Class<?> primitiveType) {
		if(primitiveType == int.class) {
			return Integer.parseInt(primitiveAsString);
		} else if(primitiveType == long.class) {
			return Long.parseLong(primitiveAsString);
		} else if(primitiveType == double.class) {
			return Double.parseDouble(primitiveAsString);
		} else if(primitiveType == float.class) {
			return Float.parseFloat(primitiveAsString);
		} else if(primitiveType == boolean.class) {
			return Boolean.parseBoolean(primitiveAsString);
		} else if(primitiveType == char.class) {
			return primitiveAsString.charAt(0);
		} else if(primitiveType == byte.class) {
			return Byte.parseByte(primitiveAsString);
		} else if(primitiveType == void.class) {
			return null;
		} else if(primitiveType == short.class) {
			return Short.parseShort(primitiveAsString);
		} else {
			return null;
		}
	}
	
	private Class<?> getClassByName(String className) {
		Class<?> classObject = null;
		classObject = PRIMITIVE_TYPES.get(className);
		if(classObject == null) {
			try {
				classObject = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return classObject;
	}

}
