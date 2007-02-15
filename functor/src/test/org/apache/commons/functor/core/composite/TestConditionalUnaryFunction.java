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
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestConditionalUnaryFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestConditionalUnaryFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestConditionalUnaryFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new ConditionalUnaryFunction(
            new Constant(true),
            new Constant("left"),
            new Constant("right"));
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
        ConditionalUnaryFunction f = new ConditionalUnaryFunction(
            new Identity(),
            new Constant("left"),
            new Constant("right"));
        assertEquals("left",f.evaluate(Boolean.TRUE));
        assertEquals("right",f.evaluate(Boolean.FALSE));
    }
    
    public void testEquals() throws Exception {
        ConditionalUnaryFunction f = new ConditionalUnaryFunction(
            new Identity(),
            new Constant("left"),
            new Constant("right"));
        assertEquals(f,f);
        assertObjectsAreEqual(f,new ConditionalUnaryFunction(
            new Identity(),
            new Constant("left"),
            new Constant("right")));
        assertObjectsAreNotEqual(f,new ConditionalUnaryFunction(
            new Identity(),
            new Constant(null),
            new Constant("right")));
        assertObjectsAreNotEqual(f,new ConditionalUnaryFunction(
            new Constant(true),
            new Constant("left"),
            new Constant("right")));
        assertObjectsAreNotEqual(f,new ConditionalUnaryFunction(
            null,
            new Constant("left"),
            new Constant("right")));
        assertObjectsAreNotEqual(f,new ConditionalUnaryFunction(
            new Constant(true),
            null,
            new Constant("right")));
        assertObjectsAreNotEqual(f,new ConditionalUnaryFunction(
            new Constant(true),
            new Constant("left"),
            null));
    }
}
