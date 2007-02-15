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
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.NoOp;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestConditionalBinaryProcedure extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestConditionalBinaryProcedure(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestConditionalBinaryProcedure.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new ConditionalBinaryProcedure(
            new Constant(true),
            new NoOp(),
            new NoOp());
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
        RunCounter left = new RunCounter();
        RunCounter right = new RunCounter();
        ConditionalBinaryProcedure p = new ConditionalBinaryProcedure(
            new LeftIdentity(),
            left,
            right);
        assertEquals(0,left.count);
        assertEquals(0,right.count);
        p.run(Boolean.TRUE,null);
        assertEquals(1,left.count);
        assertEquals(0,right.count);
        p.run(Boolean.FALSE,null);
        assertEquals(1,left.count);
        assertEquals(1,right.count);
        p.run(Boolean.TRUE,null);
        assertEquals(2,left.count);
        assertEquals(1,right.count);
    }
    
    public void testEquals() throws Exception {
        ConditionalBinaryProcedure p = new ConditionalBinaryProcedure(
            new LeftIdentity(),
            new NoOp(),
            new NoOp());
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ConditionalBinaryProcedure(
            new LeftIdentity(),
            new NoOp(),
            new NoOp()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure(
            new Constant(true),
            new NoOp(),
            new NoOp()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure(
            null,
            new NoOp(),
            new NoOp()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure(
            new LeftIdentity(),
            null,
            new NoOp()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure(
            new LeftIdentity(),
            new NoOp(),
            null));
    }

    // Classes
    // ------------------------------------------------------------------------
    
    static class RunCounter implements BinaryProcedure {        
        public void run(Object left, Object right) {
            count++;    
        }        
        public int count = 0;
    }
}
