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
package org.apache.commons.functor.core.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Identity;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestTransformedIterator extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestTransformedIterator(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestTransformedIterator.class);
    }
    
    @Override
    public Object makeFunctor() {
        List<String> list1 = new ArrayList<String>();
        list1.add("xyzzy");        
        return TransformedIterator.transform(list1.iterator(),Identity.instance());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Override
    public void setUp() throws Exception {
        super.setUp();
        list = new ArrayList<Integer>();
        negatives = new ArrayList<Integer>();
        for(int i=0;i<10;i++) {
            list.add(new Integer(i));
            negatives.add(new Integer(i*-1));
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        list = null;
        negatives = null;
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testBasicTransform() {
        Iterator<Integer> expected = negatives.iterator();
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @SuppressWarnings("unchecked")
    public void testEmptyList() {
        Iterator<?> testing = new TransformedIterator(Collections.EMPTY_LIST.iterator(),negate);
        assertTrue(!testing.hasNext());
    }

    public void testNextWithoutHasNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        Iterator<Integer> expected = negatives.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    public void testNextAfterEndOfList() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        Iterator<Integer> expected = negatives.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @SuppressWarnings("unchecked")
    public void testNextOnEmptyList() {
        Iterator<?> testing = new TransformedIterator(Collections.EMPTY_LIST.iterator(),negate);
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }
    
    public void testRemoveBeforeNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }
    
    public void testRemoveAfterNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        testing.next();
        testing.remove();
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }
    
    public void testRemoveAll() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    public void testRemoveWithoutHasNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(),negate);
        for(int i=0,m = list.size();i<m;i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }
    
    public void testTransformWithNullIteratorReturnsNull() {
        assertNull(TransformedIterator.transform(null,negate));
    }
    
    public void testTransformWithNullPredicateReturnsIdentity() {
        Iterator<Integer> iter = list.iterator();
        assertSame(iter,TransformedIterator.transform(iter,null));
    }

    public void testConstructorProhibitsNull() {
        try {
            new TransformedIterator<Object,Object>(null,null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new TransformedIterator<Integer,Integer>(null,negate);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new TransformedIterator<Integer,Integer>(list.iterator(),null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }
    

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;    
    private List<Integer> negatives = null;
    private UnaryFunction<Integer,Integer> negate = new UnaryFunction<Integer,Integer>() { 
        public Integer evaluate(Integer obj) {
            return new Integer(((Number)obj).intValue() * -1);
        }
    };
    
}
