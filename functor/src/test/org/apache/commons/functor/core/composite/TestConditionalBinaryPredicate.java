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

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestConditionalBinaryPredicate extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestConditionalBinaryPredicate(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestConditionalBinaryPredicate.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(false),
            new Constant(true));
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
            ConditionalBinaryPredicate p = new ConditionalBinaryPredicate(
                new Constant(true),
                new Constant(true),
                new Constant(false));
            assertTrue(p.test(null,null));
        }
        {
            ConditionalBinaryPredicate p = new ConditionalBinaryPredicate(
                new Constant(false),
                new Constant(true),
                new Constant(false));
            assertTrue(!p.test(null,null));
        }
    }
    
    public void testEquals() throws Exception {
        ConditionalBinaryPredicate p = new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(true),
            new Constant(false));
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(true),
            new Constant(false)));
        assertObjectsAreNotEqual(p,new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(false),
            new Constant(true)));
        assertObjectsAreNotEqual(p,new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(true),
            new Constant(true)));
        assertObjectsAreNotEqual(p,new ConditionalBinaryPredicate(
            null,
            new Constant(true),
            new Constant(true)));
        assertObjectsAreNotEqual(p,new ConditionalBinaryPredicate(
            new Constant(true),
            null,
            new Constant(true)));
        assertObjectsAreNotEqual(p,new ConditionalBinaryPredicate(
            new Constant(true),
            new Constant(true),
            null));
    }
}
