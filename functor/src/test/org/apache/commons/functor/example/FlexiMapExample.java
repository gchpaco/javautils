/*
 * Copyright 2003,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.example;

import java.util.*;

import junit.framework.*;

import org.apache.commons.functor.*;
import org.apache.commons.functor.adapter.IgnoreLeftFunction;
import org.apache.commons.functor.core.*;
import org.apache.commons.functor.core.composite.Conditional;

/*
 * ----------------------------------------------------------------------------
 * INTRODUCTION:
 * ----------------------------------------------------------------------------
 */

/*
 * In this example, we'll demonstrate how we can use "pluggable" functors
 * to create specialized Map implementations via composition.
 * 
 * All our specializations will use the same basic Map implementation.
 * Once it is built, we'll only need to define the specialized behaviors. 
 */
 
/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class FlexiMapExample extends TestCase {

    public FlexiMapExample(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(FlexiMapExample.class);
    }

    /*
     * ----------------------------------------------------------------------------
     * UNIT TESTS:
     * ----------------------------------------------------------------------------
     */

    /* 
     * In a "test first" style, let's first specify the Map behaviour we'd like
     * to implement via unit tests. 
     */
     
    /*
     * First, let's review the basic Map functionality.
     */
    
    /*
     * The basic Map interface lets one associate keys and values:
     */
    public void testBasicMap() {
        /* (We'll define these make*Map functions below.) */
        Map<Object, Object> map = makeBasicMap(); 
        String key = "key";
        Integer value = new Integer(3);
        map.put(key,value);
        assertEquals(value, map.get(key) );
    }    

    /*
     * If there is no value associated with a key,
     * the basic Map will return null for that key:
     */
    public void testBasicMapReturnsNullForMissingKey() {
        Map<Object, Object> map = makeBasicMap();
        assertNull( map.get("key") );
    }    

    /*
     * One can also explicitly store a null value for
     * some key: 
     */
    public void testBasicMapAllowsNull() {
        Map<Object, Object> map = makeBasicMap();
        Object key = "key";
        Object value = null;
        map.put(key,value);
        assertNull( map.get(key) );
    }    

    /*
     * The basic Map deals with Objects--it can store keys
     * and values of multiple or differing types:
     */
    public void testBasicMapAllowsMultipleTypes() {
        Map<Object, Object> map = makeBasicMap();
        map.put("key-1","value-1");
        map.put(new Integer(2),"value-2");
        map.put("key-3",new Integer(3));
        map.put(new Integer(4),new Integer(4));

        assertEquals("value-1", map.get("key-1") );
        assertEquals("value-2", map.get(new Integer(2)) );
        assertEquals(new Integer(3), map.get("key-3") );
        assertEquals(new Integer(4), map.get(new Integer(4)) );
    }    

    /*
     * Finally, note that putting a second value for a given
     * key will overwrite the first value--the basic Map only
     * stores the most recently put value for each key: 
     */
    public void testBasicMapStoresOnlyOneValuePerKey() {
        Map<Object, Object> map = makeBasicMap();

        assertNull( map.put("key","value-1") );
        assertEquals("value-1", map.get("key") );
        assertEquals("value-1", map.put("key","value-2"));
        assertEquals("value-2", map.get("key") );
    }    
    
    /*
     * Now let's look at some specializations of the Map behavior. 
     */
    
    /*
     * One common specialization is to forbid null values, 
     * like our old friend Hashtable: 
     */
    public void testForbidNull() {
        Map<Object, Object> map = makeNullForbiddenMap();
        
        map.put("key","value");
        map.put("key2", new Integer(2) );
        try {
            map.put("key3",null);
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }                
    }

    /*
     * Alternatively, we may want to provide a default
     * value to return when null is associated with some 
     * key. (This might be useful, for example, when the Map
     * contains a counter--when there's no count yet, we'll 
     * want to treat it as zero.):  
     */
    public void testNullDefaultsToZero() {
        Map<Object, Object> map = makeDefaultValueForNullMap(new Integer(0));        
        /*
         * We expect 0 when no value has been associated with "key".
         */
        assertEquals( new Integer(0), map.get("key") );
        /*
         * We also expect 0 when a null value has been associated with "key".
         */
        map.put("key", null);
        assertEquals( new Integer(0), map.get("key") );
    }

    /*
     * Another common specialization is to constrain the type of values
     * that may be stored in the Map:
     */
	public void testIntegerValuesOnly() {
		Map<Object, Object> map = makeTypeConstrainedMap(Integer.class);
		map.put("key", new Integer(2));        
		assertEquals( new Integer(2), map.get("key") );
		try {
			map.put("key2","value");
			fail("Expected ClassCastException");
		} catch(ClassCastException e) {
			// expected
		}                		
	}

    /*
     * A more interesting specialization is that used by the 
     * Jakarta Commons Collections MultiMap class, which allows 
     * one to associate multiple values with each key.  The put
     * function still accepts a single value, but the get function
     * will return a Collection of values.  Associating multiple values
     * with a key adds to that collection, rather than overwriting the
     * previous value: 
     */
	public void testMultiMap() {
		Map<Object, Object> map = makeMultiMap();

		map.put("key", "value 1");
		
		{
			Collection<?> result = (Collection)(map.get("key"));
			assertEquals(1,result.size());
			assertEquals("value 1", result.iterator().next());
		}

		map.put("key", "value 2");

		{
			Collection<?> result = (Collection)(map.get("key"));
			assertEquals(2,result.size());
			Iterator<?> iter = result.iterator();
			assertEquals("value 1", iter.next());
			assertEquals("value 2", iter.next());
		}

		map.put("key", "value 3");

		{
			Collection<?> result = (Collection)(map.get("key"));
			assertEquals(3,result.size());
			Iterator<?> iter = result.iterator();
			assertEquals("value 1", iter.next());
			assertEquals("value 2", iter.next());
			assertEquals("value 3", iter.next());
		}

	}

    /*
     * Here's another variation on the MultiMap theme.  
     * Rather than adding elements to a Collection, let's 
     * concatenate String values together, delimited by commas.
     * (Such a Map might be used by the Commons Collection's
     * ExtendedProperties type.): 
     */
	public void testStringConcatMap() {
		Map<Object, Object> map = makeStringConcatMap();
		map.put("key", "value 1");
		assertEquals("value 1",map.get("key"));
		map.put("key", "value 2");
		assertEquals("value 1, value 2",map.get("key"));
		map.put("key", "value 3");
		assertEquals("value 1, value 2, value 3",map.get("key"));
	}

    /*
     * ----------------------------------------------------------------------------
     * THE GENERIC MAP IMPLEMENTATION:
     * ----------------------------------------------------------------------------
     */

    /*
     * How can one Map implementation support all these behaviors?
     * Using functors and composition, of course.  
     * 
     * In order to keep our example small, we'll just consider the 
     * primary Map.put and Map.get methods here, although the remaining
     * Map methods could be handled similiarly.    
     */
    static class FlexiMap<K,V> implements Map<K,V> {

        /*
         * Our FlexiMap will accept two BinaryFunctions, one 
         * that's used to transform objects being put into the Map,
         * and one that's used to transforms objects being retrieved 
         * from the map.
         */
        @SuppressWarnings("unchecked")
        public FlexiMap(BinaryFunction<Object,Object,V> putfn, BinaryFunction<Object,Object,V> getfn) {
            onPut = null == putfn ? RightIdentity.instance() : putfn;
            onGet = null == getfn ? RightIdentity.instance() : getfn;
            proxiedMap = new HashMap<K,V>();
        }        

        
        /*
         * The arguments to our "onGet" function will be the 
         * key and the value associated with that key in the 
         * underlying Map.  We'll return whatever the function
         * returns.
         */
        public V get(Object key) {
            return onGet.evaluate( key, proxiedMap.get(key) );
        }

        /*
         * The arguments to our "onPut" function will be the 
         * value previously associated with that key (if any),
         * as well as the new value being associated with that key.
         * 
         * Since put returns the previously associated value, 
         * we'll invoke onGet here as well. 
         */
        @SuppressWarnings("unchecked")
        public V put(Object key, Object value) {
            Object oldvalue = proxiedMap.get(key);
            proxiedMap.put((K) key, onPut.evaluate(oldvalue, value));
            return onGet.evaluate(key,oldvalue);
        }

       /* 
        * We'll skip the remaining Map methods for now.    
        */
        
        public void clear() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public boolean containsKey(Object key) {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public boolean containsValue(Object value) {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public Set<Entry<K, V>> entrySet() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public boolean isEmpty() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public Set<K> keySet() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public void putAll(Map<? extends K, ? extends V> t) {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public V remove(Object key) {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public int size() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        public Collection<V> values() {
            throw new UnsupportedOperationException("Left as an exercise for the reader.");
        }

        private BinaryFunction<Object,Object,V> onPut = null;
        private BinaryFunction<Object,Object,V> onGet = null;
        private Map<K,V> proxiedMap = null;
    }

    /*
     * ----------------------------------------------------------------------------
     * MAP SPECIALIZATIONS:
     * ----------------------------------------------------------------------------
     */

    /*
     * For the "basic" Map, we'll simply create a HashMap.
     * Note that using a RightIdentity for onPut and onGet
     * would yield the same behavior. 
     */
    private Map<Object,Object> makeBasicMap() {
        return new HashMap<Object,Object>();
    }
    
    /*
     * To prohibit null values, we'll only need to 
     * provide an onPut function.
     */
    @SuppressWarnings("unchecked")
    private Map<Object, Object> makeNullForbiddenMap() {
        return new FlexiMap<Object,Object>(
            /*
             * We simply ignore the left-hand argument,
             */
            IgnoreLeftFunction.adapt(                  
                /*
                 * and for the right-hand,
                 */      
                Conditional.function(
                    /*
                     * we'll test for null,
                     */      
                    IsNull.instance(),
                    throwNPE,
                    /*
                     * and passing through all non-null values.
                     */      
                    Identity.instance()
                )
            ),
            null
        );
    }

    /*
     * To provide a default for null values, we'll only need to 
     * provide an onGet function, simliar to the onPut method used
     * above.
     */
	@SuppressWarnings("unchecked")
  private Map<Object, Object> makeDefaultValueForNullMap(Object defaultValue) {
		return new FlexiMap<Object, Object>(
            null,
            /*
             * We ignore the left-hand argument,
             */
			IgnoreLeftFunction.adapt(                        
                /*
                 * and for the right-hand,
                 */      
				Conditional.function(
                    /*
                     * we'll test for null,
                     */      
					IsNull.instance(),
                    /*
                     * returning our default when the value is otherwise null,
                     */      
					new Constant(defaultValue),
                    /*
                     * and passing through all non-null values.
                     */      
					Identity.instance()
				)
			)
		);
	}

    /*
     * To constrain the value types, we'll 
     * provide an onPut function,
     */
	@SuppressWarnings("unchecked")
  private Map<Object, Object> makeTypeConstrainedMap(Class clazz) {
		return new FlexiMap(
            /*
             * ignore the left-hand argument,
             */
			IgnoreLeftFunction.adapt(                        
				Conditional.function(
                    /*
                     * we'll test the type of the right-hand argument,
                     */      
					new IsInstanceOf(clazz),
                    /*
                     * and either pass the given value through,
                     */      
					Identity.instance(),
                    throwCCE
				)
			),
			null
		);
	}

    /*
     * The MultiMap is a bit more interesting, since we'll
     * need to consider both the old and new values during
     * onPut:
     */
	private Map<Object, Object> makeMultiMap() {
		return new FlexiMap<Object,Object>(
			new BinaryFunction<Object, Object, Object>() {
				@SuppressWarnings("unchecked")
        public Object evaluate(Object oldval, Object newval) {
					List<Object> list = null;
					if(null == oldval) {
						list = new ArrayList<Object>();
					} else {
						list = (List)oldval;
					}
					list.add(newval);
					return list;
				}
			},
			null
		);
	}

    /*
     * The StringConcatMap is more interesting still.
     */
	@SuppressWarnings("unchecked")
  private Map<Object, Object> makeStringConcatMap() {
		return new FlexiMap(
            /*
             * The onPut function looks similiar to the MultiMap
             * method:
             */
			new BinaryFunction() {
				public Object evaluate(Object oldval, Object newval) {
					StringBuffer buf = null;
					if(null == oldval) {
						buf = new StringBuffer();
					} else {
						buf = (StringBuffer)oldval;
						buf.append(", ");
					}
					buf.append(newval);
					return buf;
				}
			},
            /*
             * but we'll also need an onGet functor to convert
             * the StringBuffer to a String:
             */
			new BinaryFunction() {
				public Object evaluate(Object key, Object val) {
					if(null == val) {
						return null;
					}
          return ((StringBuffer)val).toString();
				}
			}
		);
	}

    /*
     * (This "UniversalFunctor" type provides a functor 
     * that takes the same action regardless of the number of
     * parameters. We used it above to throw Exceptions when 
     * needed.)
     */
     
    private abstract class UniversalFunctor implements 
        Procedure, UnaryProcedure<Object>, BinaryProcedure<Object, Object>, 
        Function<Object>, UnaryFunction<Object, Object>, BinaryFunction<Object, Object, Object> {
        public abstract void run();

        public void run(Object obj) {
            run();
        }
        public void run(Object left, Object right) {
            run();
        }
        public Object evaluate() {
            run();
            return null;
        }
        public Object evaluate(Object obj) {
            run();
            return null;
        }
        public Object evaluate(Object left, Object right) {
            run();
            return null;
        }
    }

	private UniversalFunctor throwNPE = new UniversalFunctor() {
		@Override
    public void run() {
			throw new NullPointerException();
		}
	};
    
	private UniversalFunctor throwCCE = new UniversalFunctor() {
		@Override
    public void run() {
			throw new ClassCastException();
		}
	};
    
}
