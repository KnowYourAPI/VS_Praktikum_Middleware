package mware_lib;

import java.lang.reflect.Method;
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
		
		for(int i = 0; i <= numberOfParameters; i += 2) {
			typeStrings[i] = splittedMessage[i+3]; // 3er offset + 0 fuer Typ
			parameterStrings[i] = splittedMessage[i+4]; // 3er offset + 1 fuer Wert
		}
		
		Class<?>[] parameterTypes = new Class<?>[numberOfParameters];
		
		for(int i= 0; i < numberOfParameters; i++) {
			parameterTypes[i] = getClassByName(typeStrings[i]);
		}
		
		// Methodenaufruf
		
		Class<?> realObjectClass = realObject.getClass();
		
		Method toBeInvoked = realObjectClass.getMethod(methodName, parameterTypes);
		
		Object returnValue = toBeInvoked.invoke(realObject, args);
		
		String returnMessage = "RETURN%" + returnValue.getClass().getName() + "%";
		
		return returnMessage;
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
