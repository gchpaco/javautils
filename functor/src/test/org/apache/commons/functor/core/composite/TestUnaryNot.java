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
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestUnaryNot extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestUnaryNot(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnaryNot.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryNot(new Constant(true));
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
        UnaryPredicate truePred = new UnaryNot(new Constant(false));
        assertTrue(truePred.test(null));
        assertTrue(truePred.test("xyzzy"));
        assertTrue(truePred.test(new Integer(3)));
    }
    
    public void testEquals() throws Exception {
        UnaryNot p = new UnaryNot(Constant.truePredicate());
        assertEquals(p,p);
        assertObjectsAreEqual(p,new UnaryNot(new Constant(true)));
        assertObjectsAreEqual(p,UnaryNot.not(new Constant(true)));
        assertObjectsAreNotEqual(p,new UnaryNot(new Constant(false)));
        assertObjectsAreNotEqual(p,Constant.truePredicate());
        assertObjectsAreNotEqual(p,new UnaryNot(null));
    }

    public void testNotNull() throws Exception {
        assertNull(UnaryNot.not(null));
    }

    public void testNotNotNull() throws Exception {
        assertNotNull(UnaryNot.not(Constant.truePredicate()));
    }
}
