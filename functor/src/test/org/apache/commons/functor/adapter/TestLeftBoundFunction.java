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
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestLeftBoundFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestLeftBoundFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestLeftBoundFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new LeftBoundFunction(new RightIdentity(),"xyzzy");
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

    public void testEvaluate() throws Exception {
        UnaryFunction f = new LeftBoundFunction(new RightIdentity(),"foo");
        assertEquals("xyzzy",f.evaluate("xyzzy"));
    }
    
    public void testEquals() throws Exception {
        UnaryFunction f = new LeftBoundFunction(new RightIdentity(),"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new LeftBoundFunction(new RightIdentity(),"xyzzy"));
        assertObjectsAreNotEqual(f,new Constant("xyzzy"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction(new LeftIdentity(),"xyzzy"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction(new RightIdentity(),"bar"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction(null,"xyzzy"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction(new RightIdentity(),null));
        assertObjectsAreEqual(new LeftBoundFunction(null,null),new LeftBoundFunction(null,null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(LeftBoundFunction.bind(null,"xyzzy"));
    }

    public void testAdapt() throws Exception {
        assertNotNull(LeftBoundFunction.bind(new RightIdentity(),"xyzzy"));
        assertNotNull(LeftBoundFunction.bind(new RightIdentity(),null));
    }
}
