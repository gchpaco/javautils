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
import org.apache.commons.functor.core.RightIdentity;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestLeftBoundPredicate extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestLeftBoundPredicate(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestLeftBoundPredicate.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new LeftBoundPredicate(new Constant(true),"xyzzy");
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
        UnaryPredicate p = new LeftBoundPredicate(new BinaryFunctionBinaryPredicate(new RightIdentity()),"foo");
        assertEquals(true,p.test(Boolean.TRUE));
        assertEquals(false,p.test(Boolean.FALSE));
    }
    
    public void testEquals() throws Exception {
        UnaryPredicate p = new LeftBoundPredicate(new Constant(true),"xyzzy");
        assertEquals(p,p);
        assertObjectsAreEqual(p,new LeftBoundPredicate(new Constant(true),"xyzzy"));
        assertObjectsAreNotEqual(p,new Constant(true));
        assertObjectsAreNotEqual(p,new LeftBoundPredicate(new Constant(false),"xyzzy"));
        assertObjectsAreNotEqual(p,new LeftBoundPredicate(new Constant(true),"foo"));
        assertObjectsAreNotEqual(p,new LeftBoundPredicate(null,"xyzzy"));
        assertObjectsAreNotEqual(p,new LeftBoundPredicate(new Constant(true),null));
        assertObjectsAreEqual(new LeftBoundPredicate(null,null),new LeftBoundPredicate(null,null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(LeftBoundPredicate.bind(null,"xyzzy"));
    }

    public void testAdapt() throws Exception {
        assertNotNull(LeftBoundPredicate.bind(new Constant(false),"xyzzy"));
        assertNotNull(LeftBoundPredicate.bind(new Constant(false),null));
    }
}
