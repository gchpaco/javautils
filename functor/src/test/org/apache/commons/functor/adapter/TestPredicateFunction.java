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
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestPredicateFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestPredicateFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestPredicateFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new PredicateFunction(new Constant(true));
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
        Function f = new PredicateFunction(new Constant(true));
        assertEquals(Boolean.TRUE,f.evaluate());
    }
    
    public void testTestWhenFalse() throws Exception {
        Function f = new PredicateFunction(new Constant(false));
        assertEquals(Boolean.FALSE,f.evaluate());
    }

    public void testEquals() throws Exception {
        Function f = new PredicateFunction(new Constant(true));
        assertEquals(f,f);
        assertObjectsAreEqual(f,new PredicateFunction(new Constant(true)));
        assertObjectsAreNotEqual(f,new Constant("x"));
        assertObjectsAreNotEqual(f,new PredicateFunction(new Constant(false)));
        assertObjectsAreNotEqual(f,new PredicateFunction(null));
        assertObjectsAreEqual(new PredicateFunction(null),new PredicateFunction(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(FunctionPredicate.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(PredicateFunction.adapt(new Constant(true)));
    }
}
