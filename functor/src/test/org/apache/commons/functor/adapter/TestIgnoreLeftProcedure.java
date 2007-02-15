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
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.NoOp;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestIgnoreLeftProcedure extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestIgnoreLeftProcedure(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestIgnoreLeftProcedure.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IgnoreLeftProcedure(new NoOp());
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
        BinaryProcedure p = new IgnoreLeftProcedure(new UnaryFunctionUnaryProcedure(new Identity()));
        p.run(null,Boolean.TRUE);
    }
    
    public void testEquals() throws Exception {
        BinaryProcedure p = new IgnoreLeftProcedure(new NoOp());
        assertEquals(p,p);
        assertObjectsAreEqual(p,new IgnoreLeftProcedure(new NoOp()));
        assertObjectsAreNotEqual(p,new NoOp());
        assertObjectsAreNotEqual(p,new IgnoreLeftProcedure(null));
        assertObjectsAreEqual(new IgnoreLeftProcedure(null),new IgnoreLeftProcedure(null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(IgnoreLeftProcedure.adapt(null));
    }

    public void testAdapt() throws Exception {
        assertNotNull(IgnoreLeftProcedure.adapt(new NoOp()));
    }
}
