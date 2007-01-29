package util.annotations;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class Annotations
{
    public static List<Method> getAnnotatedMethods (Class<?> target,
					            Class<? extends Annotation>
					            annotation)
    {
        List<Method> methods = new ArrayList<Method> ();
	for (Method m : target.getMethods ())
	    if (m.isAnnotationPresent (annotation))
		methods.add (m);
	return methods;
    }
    public static Method getAnnotatedMethod (Class<?> target,
					     Class<? extends Annotation>
					     annotation)
    {
	for (Method m : target.getMethods ())
	    if (m.isAnnotationPresent (annotation))
		return m;
	return null;
    }
    public static Field getAnnotatedField (Class<?> target,
					   Class<? extends Annotation>
					   annotation)
    {
	for (Field f : target.getFields ())
	    if (f.isAnnotationPresent (annotation))
		return f;
	return null;
    }
}
