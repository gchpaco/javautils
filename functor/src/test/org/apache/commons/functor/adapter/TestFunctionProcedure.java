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
public class TestFunctionProcedure extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestFunctionProcedure(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestFunctionProcedure.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new FunctionProcedure(new Constant("K"));
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

    public void testRun() throws Exception {
        class EvaluateCounter implements Function {
            int count = 0;
            public Object evaluate() { return new Integer(count++); }
        }
        EvaluateCounter counter = new EvaluateCounter();
        Procedure p = new FunctionProcedure(counter);
        assertEquals(0,counter.count);
        p.run();
        assertEquals(1,counter.count);
        p.run();
        assertEquals(2,counter.count);
    }

    public void testEquals() throws Exception {
        Procedure p = new FunctionProcedure(new Constant("K"));
        assertEquals(p,p);
        assertObjectsAreEqual(p,new FunctionProcedure(new Constant("K")));
        assertObjectsAreNotEqual(p,new NoOp());
        assertObjectsAreNotEqual(p,new FunctionProcedure(null));
        assertObjectsAreNotEqual(p,new FunctionProcedure(new Constant("J")));
        assertObjectsAreEqual(new FunctionProcedure(null),new FunctionProcedure(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(FunctionProcedure.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(FunctionProcedure.adapt(new Constant("K")));
    }
}
