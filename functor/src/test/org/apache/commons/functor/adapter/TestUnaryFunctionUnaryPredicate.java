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
package org.apache.commons.functor.adapter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestUnaryFunctionUnaryPredicate extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestUnaryFunctionUnaryPredicate(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnaryFunctionUnaryPredicate.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryFunctionUnaryPredicate(new Constant(Boolean.TRUE));
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

    public void testTestWhenTrue() throws Exception {
        UnaryPredicate p = new UnaryFunctionUnaryPredicate(new Constant(Boolean.TRUE));
        assertTrue(p.test(null));
    }
    
    public void testTestWhenFalse() throws Exception {
        UnaryPredicate p = new UnaryFunctionUnaryPredicate(new Constant(Boolean.FALSE));
        assertTrue(!p.test(null));
    }

    public void testTestWhenNull() throws Exception {
        UnaryPredicate p = new UnaryFunctionUnaryPredicate(new Constant(null));
        try {
            p.test("xyzzy");
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }
    
    public void testTestWhenNonBoolean() throws Exception {
        UnaryPredicate p = new UnaryFunctionUnaryPredicate(new Constant(new Integer(2)));
        try {
            p.test("xyzzy");
            fail("Expected ClassCastException");
        } catch(ClassCastException e) {
            // expected
        }
    }
    
    public void testEquals() throws Exception {
        UnaryPredicate p = new UnaryFunctionUnaryPredicate(new Constant(Boolean.TRUE));
        assertEquals(p,p);
        assertObjectsAreEqual(p,new UnaryFunctionUnaryPredicate(new Constant(Boolean.TRUE)));
        assertObjectsAreNotEqual(p,Constant.truePredicate());
        assertObjectsAreNotEqual(p,new UnaryFunctionUnaryPredicate(null));
        assertObjectsAreNotEqual(p,new UnaryFunctionUnaryPredicate(new Constant(Boolean.FALSE)));
        assertObjectsAreEqual(new UnaryFunctionUnaryPredicate(null),new UnaryFunctionUnaryPredicate(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(UnaryFunctionUnaryPredicate.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(UnaryFunctionUnaryPredicate.adapt(new Constant(Boolean.TRUE)));
    }
}
