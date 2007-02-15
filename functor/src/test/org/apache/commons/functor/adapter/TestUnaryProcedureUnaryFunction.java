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
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestUnaryProcedureUnaryFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestUnaryProcedureUnaryFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnaryProcedureUnaryFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryProcedureUnaryFunction(new NoOp());
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
        UnaryFunction f = new UnaryProcedureUnaryFunction(new NoOp());
        assertNull(f.evaluate(null));
    }
    
    public void testEquals() throws Exception {
        UnaryFunction f = new UnaryProcedureUnaryFunction(new NoOp());
        assertEquals(f,f);
        assertObjectsAreEqual(f,new UnaryProcedureUnaryFunction(new NoOp()));
        assertObjectsAreNotEqual(f,new Constant("x"));
        assertObjectsAreNotEqual(f,new UnaryProcedureUnaryFunction(new UnaryProcedure() { public void run(Object a) { } }));
        assertObjectsAreNotEqual(f,new Constant(null));
        assertObjectsAreNotEqual(f,new UnaryProcedureUnaryFunction(null));
        assertObjectsAreEqual(new UnaryProcedureUnaryFunction(null),new UnaryProcedureUnaryFunction(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(UnaryFunctionUnaryProcedure.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(UnaryProcedureUnaryFunction.adapt(new NoOp()));
    }
}
