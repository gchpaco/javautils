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
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestFilteredIterator extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestFilteredIterator(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestFilteredIterator.class);
    }

    @Override
    public Object makeFunctor() {
        List<String> lst = new ArrayList<String>();
        lst.add("xyzzy");        
        return FilteredIterator.filter(lst.iterator(),Constant.truePredicate());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Override
    public void setUp() throws Exception {
        super.setUp();
        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        for(int i=0;i<10;i++) {
            list.add(new Integer(i));
            if(i%2 == 0) {
                evens.add(new Integer(i));
            }
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        list = null;
        evens = null;
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testSomePass() {
        Iterator<Integer> expected = evens.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    public void testAllPass() {
        Iterator<Integer> expected = evens.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(evens.iterator(),isEven);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    public void testAllPass2() {
        Iterator<Integer> expected = list.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @SuppressWarnings("unchecked")
    public void testEmptyList() {
        Iterator<?> testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),isEven);
        assertTrue(!testing.hasNext());
    }

    @SuppressWarnings("unchecked")
    public void testNonePass() {
        Iterator<?> testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),Constant.falsePredicate());
        assertTrue(!testing.hasNext());
    }

    public void testNextWithoutHasNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        Iterator<Integer> expected = evens.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    public void testNextAfterEndOfList() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        Iterator<Integer> expected = evens.iterator();
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
        Iterator<?> testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),isEven);
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }
    
    public void testRemoveBeforeNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }
    
    public void testRemoveAfterNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        testing.next();
        testing.remove();
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }
    
    public void testRemoveSome() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        for(Iterator<Integer> iter = list.iterator(); iter.hasNext();) {
            assertTrue(! isEven.test(iter.next()) );
        }
    }

    public void testRemoveAll() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    public void testRemoveWithoutHasNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        for(int i=0,m = list.size();i<m;i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }
    
    public void testFilterWithNullIteratorReturnsNull() {
        assertNull(FilteredIterator.filter(null,Constant.truePredicate()));
    }
    
    public void testFilterWithNullPredicateReturnsIdentity() {
        Iterator<Integer> iter = list.iterator();
        assertSame(iter,FilteredIterator.filter(iter,null));
    }

    public void testConstructorProhibitsNull() {
        try {
            new FilteredIterator<Object>(null,null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new FilteredIterator<Object>(null,Constant.truePredicate());
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new FilteredIterator<Integer>(list.iterator(),null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }
    

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;    
    private List<Integer> evens = null;
    private UnaryPredicate<Integer> isEven = new UnaryPredicate<Integer>() { 
        public boolean test(Integer obj) {
            return ((Number)obj).intValue() % 2 == 0;
        }
    };
    
}
