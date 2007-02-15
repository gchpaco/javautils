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

import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestIsGreaterThanOrEqual extends BaseComparisonPredicateTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIsGreaterThanOrEqual(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIsGreaterThanOrEqual.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsGreaterThanOrEqual();
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
        IsGreaterThanOrEqual p = new IsGreaterThanOrEqual();
        assertTrue(!p.test(new Integer(2),new Integer(4)));
        assertTrue(!p.test(new Integer(3),new Integer(4)));
        assertTrue(p.test(new Integer(4),new Integer(4)));
        assertTrue(p.test(new Integer(5),new Integer(4)));
        assertTrue(p.test(new Integer(6),new Integer(4)));
    }
    
    public void testInstance() {
        assertTrue(IsGreaterThanOrEqual.instance(new Integer(7)).test(new Integer(8)));
        assertTrue(! IsGreaterThanOrEqual.instance(new Integer(7)).test(new Integer(6)));
    }

    public void testEquals() throws Exception {
        IsGreaterThanOrEqual p = new IsGreaterThanOrEqual();
        assertEquals(p,p);

        assertObjectsAreEqual(p,new IsGreaterThanOrEqual());
        assertObjectsAreEqual(p,new IsGreaterThanOrEqual(null));
        assertObjectsAreEqual(p,new IsGreaterThanOrEqual(new ComparableComparator()));
        assertObjectsAreEqual(p,IsGreaterThanOrEqual.instance());
        assertSame(IsGreaterThanOrEqual.instance(),IsGreaterThanOrEqual.instance());
        assertObjectsAreNotEqual(p,new Constant(false));
    }
    
}
