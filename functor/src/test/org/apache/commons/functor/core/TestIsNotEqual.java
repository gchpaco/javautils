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
package org.apache.commons.functor.core;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestIsNotEqual extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIsNotEqual(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIsNotEqual.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsNotEqual();
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
        IsNotEqual p = new IsNotEqual();
        assertTrue("For symmetry, two nulls should be equal",!p.test(null,null));
        assertTrue(!p.test("foo","foo"));
        assertTrue(p.test(null,"foo"));
        assertTrue(p.test("foo",null));
        assertTrue(!p.test(new Integer(3),new Integer(3)));
        assertTrue(p.test(null,new Integer(3)));
        assertTrue(p.test(new Integer(3),null));

        assertTrue(p.test(new Integer(3),new Integer(4)));
        assertTrue(p.test(new Integer(4),new Integer(3)));
        assertTrue(p.test("3",new Integer(3)));
        assertTrue(p.test(new Integer(3),"3"));
    }
        
    public void testEquals() throws Exception {
        BinaryPredicate p = new IsNotEqual();
        assertEquals(p,p);
        assertObjectsAreEqual(p,new IsNotEqual());
        assertObjectsAreEqual(p,IsNotEqual.instance());
        assertObjectsAreNotEqual(p,Constant.truePredicate());
    }

    public void testConstant() throws Exception {
        assertEquals(IsNotEqual.instance(),IsNotEqual.instance());
        assertSame(IsNotEqual.instance(),IsNotEqual.instance());
    }
}
