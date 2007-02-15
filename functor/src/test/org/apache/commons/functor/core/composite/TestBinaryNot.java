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
package org.apache.commons.functor.core.composite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestBinaryNot extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestBinaryNot(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestBinaryNot.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new BinaryNot(new Constant(true));
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
        BinaryPredicate truePred = new BinaryNot(new Constant(false));
        assertTrue(truePred.test(null,null));
        assertTrue(truePred.test("xyzzy","abcde"));
        assertTrue(truePred.test("xyzzy",new Integer(3)));
    }
    
    public void testEquals() throws Exception {
        BinaryNot p = new BinaryNot(Constant.truePredicate());
        assertEquals(p,p);
        assertObjectsAreEqual(p,new BinaryNot(new Constant(true)));
        assertObjectsAreEqual(p,BinaryNot.not(new Constant(true)));
        assertObjectsAreNotEqual(p,new BinaryNot(new Constant(false)));
        assertObjectsAreNotEqual(p,Constant.truePredicate());
        assertObjectsAreNotEqual(p,new BinaryNot(null));
    }

    public void testNotNull() throws Exception {
        assertNull(BinaryNot.not(null));
    }

    public void testNotNotNull() throws Exception {
        assertNotNull(BinaryNot.not(Constant.truePredicate()));
    }
}
