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
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestProcedureFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestProcedureFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestProcedureFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new ProcedureFunction(new NoOp());
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
        Function f = new ProcedureFunction(new NoOp());
        assertNull(f.evaluate());
    }
    
    public void testEquals() throws Exception {
        Function f = new ProcedureFunction(new NoOp());
        assertEquals(f,f);
        assertObjectsAreEqual(f,new ProcedureFunction(new NoOp()));
        assertObjectsAreNotEqual(f,new Constant("x"));
        assertObjectsAreNotEqual(f,new ProcedureFunction(new Procedure() { public void run() { } }));
        assertObjectsAreNotEqual(f,new Constant(null));
        assertObjectsAreNotEqual(f,new ProcedureFunction(null));
        assertObjectsAreEqual(new ProcedureFunction(null),new ProcedureFunction(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(FunctionProcedure.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(ProcedureFunction.adapt(new NoOp()));
    }
}