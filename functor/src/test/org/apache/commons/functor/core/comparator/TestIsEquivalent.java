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
public class TestIsEquivalent extends BaseComparisonPredicateTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIsEquivalent(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIsEquivalent.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsEquivalent();
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
        IsEquivalent p = new IsEquivalent();
        assertTrue(!p.test(new Integer(2),new Integer(4)));
        assertTrue(!p.test(new Integer(3),new Integer(4)));
        assertTrue(p.test(new Integer(4),new Integer(4)));
        assertTrue(!p.test(new Integer(5),new Integer(4)));
        assertTrue(!p.test(new Integer(6),new Integer(4)));
    }
    
    public void testInstance() {
        assertTrue(IsEquivalent.instance("Xyzzy").test("Xyzzy"));
        assertTrue(!IsEquivalent.instance("Xyzzy").test("z"));
    }
    
    public void testEquals() throws Exception {
        IsEquivalent p = new IsEquivalent();
        assertEquals(p,p);

        assertObjectsAreEqual(p,new IsEquivalent());
        assertObjectsAreEqual(p,new IsEquivalent(null));
        assertObjectsAreEqual(p,new IsEquivalent(new ComparableComparator()));
        assertObjectsAreEqual(p,IsEquivalent.instance());
        assertSame(IsEquivalent.instance(),IsEquivalent.instance());
        assertObjectsAreNotEqual(p,new Constant(false));
    }
    
}
