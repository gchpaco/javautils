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
	// if we could use getMethods () from within JPF, the following
	// recursion would not be necessary.  Sigh.
	for (Method m : target.getDeclaredMethods ())
	    if (m.getAnnotation (annotation) != null)
	        methods.add (m);
	Class sup = target.getSuperclass ();
	if (sup != null) methods.addAll (getAnnotatedMethods (sup, annotation));
	return methods;
    }
    public static Method getAnnotatedMethod (Class<?> target,
					     Class<? extends Annotation>
					     annotation)
    {
	// if we could use getMethods () from within JPF, the following
	// recursion would not be necessary.  Sigh.
	for (Method m : target.getDeclaredMethods ())
	    if (m.getAnnotation (annotation) != null)
	        return m;
	Class sup = target.getSuperclass ();
	return sup == null ? null : getAnnotatedMethod (sup, annotation);
    }
    public static Field getAnnotatedField (Class<?> target,
					   Class<? extends Annotation>
					   annotation)
    {
	// if we could use getFields () from within JPF, the following
	// recursion would not be necessary.  Sigh.
	for (Field f : target.getDeclaredFields ())
	    if (f.getAnnotation (annotation) != null)
	        return f;
	Class sup = target.getSuperclass ();
	return sup == null ? null : getAnnotatedField (sup, annotation);
    }
}
