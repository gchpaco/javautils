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
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestBoundPredicate extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestBoundPredicate(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestBoundPredicate.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new BoundPredicate(new Constant(true),"xyzzy");
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
        {
            Predicate p = new BoundPredicate(new UnaryFunctionUnaryPredicate(new Identity()),Boolean.TRUE);
            assertEquals(true,p.test());
        }
        {
            Predicate p = new BoundPredicate(new UnaryFunctionUnaryPredicate(new Identity()),Boolean.FALSE);
            assertEquals(false,p.test());
        }
    }
    
    public void testEquals() throws Exception {
        Predicate f = new BoundPredicate(new Constant(true),"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new BoundPredicate(new Constant(true),"xyzzy"));
        assertObjectsAreNotEqual(f,new Constant(true));
        assertObjectsAreNotEqual(f,new BoundPredicate(new Constant(true),"foo"));
        assertObjectsAreNotEqual(f,new BoundPredicate(new Constant(false),"xyzzy"));
        assertObjectsAreNotEqual(f,new BoundPredicate(null,"xyzzy"));
        assertObjectsAreNotEqual(f,new BoundPredicate(new Constant(true),null));
        assertObjectsAreEqual(new BoundPredicate(null,null),new BoundPredicate(null,null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(BoundPredicate.bind(null,"xyzzy"));
    }

    public void testAdapt() throws Exception {
        assertNotNull(BoundPredicate.bind(new Constant(true),"xyzzy"));
        assertNotNull(BoundPredicate.bind(new Constant(true),null));
    }
}
