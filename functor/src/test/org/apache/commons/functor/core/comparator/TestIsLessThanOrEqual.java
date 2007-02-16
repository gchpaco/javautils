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
package org.apache.commons.functor.core.comparator;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestIsLessThanOrEqual extends BaseComparisonPredicateTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIsLessThanOrEqual(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIsLessThanOrEqual.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsLessThanOrEqual();
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testTest() throws Exception {
        IsLessThanOrEqual p = new IsLessThanOrEqual();
        assertTrue(p.test(new Integer(2),new Integer(4)));
        assertTrue(p.test(new Integer(3),new Integer(4)));
        assertTrue(p.test(new Integer(4),new Integer(4)));
        assertTrue(!p.test(new Integer(5),new Integer(4)));
        assertTrue(!p.test(new Integer(6),new Integer(4)));
    }
    
    public void testInstance() {
        assertTrue(! IsLessThanOrEqual.instance(new Integer(7)).test(new Integer(8)));
        assertTrue(IsLessThanOrEqual.instance(new Integer(7)).test(new Integer(6)));
    }
    
    public void testEquals() throws Exception {
        IsLessThanOrEqual p = new IsLessThanOrEqual();
        assertEquals(p,p);

        assertObjectsAreEqual(p,new IsLessThanOrEqual());
        assertObjectsAreEqual(p,new IsLessThanOrEqual(null));
        assertObjectsAreEqual(p,new IsLessThanOrEqual(new ComparableComparator()));
        assertObjectsAreEqual(p,IsLessThanOrEqual.instance());
        assertSame(IsLessThanOrEqual.instance(),IsLessThanOrEqual.instance());
        assertObjectsAreNotEqual(p,new Constant(false));
    }
    
}
