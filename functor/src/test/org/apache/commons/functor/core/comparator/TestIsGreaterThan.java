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
public class TestIsGreaterThan extends BaseComparisonPredicateTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIsGreaterThan(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIsGreaterThan.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsGreaterThan();
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
        IsGreaterThan p = new IsGreaterThan();
        assertTrue(!p.test(new Integer(2),new Integer(4)));
        assertTrue(!p.test(new Integer(3),new Integer(4)));
        assertTrue(!p.test(new Integer(4),new Integer(4)));
        assertTrue(p.test(new Integer(5),new Integer(4)));
        assertTrue(p.test(new Integer(6),new Integer(4)));
    }
    
    public void testInstance() {
        assertTrue(IsGreaterThan.instance(new Integer(7)).test(new Integer(8)));
        assertTrue(! IsGreaterThan.instance(new Integer(7)).test(new Integer(6)));
    }
    
    public void testEquals() throws Exception {
        IsGreaterThan p = new IsGreaterThan();
        assertEquals(p,p);

        assertObjectsAreEqual(p,new IsGreaterThan());
        assertObjectsAreEqual(p,new IsGreaterThan(null));
        assertObjectsAreEqual(p,new IsGreaterThan(new ComparableComparator()));
        assertObjectsAreEqual(p,IsGreaterThan.instance());
        assertSame(IsGreaterThan.instance(),IsGreaterThan.instance());
        assertObjectsAreNotEqual(p,new Constant(false));
    }
    
}
