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
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.IsNull;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.comparator.IsGreaterThan;
import org.apache.commons.functor.core.comparator.Max;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestConditional extends TestCase {

    // Conventional
    // ------------------------------------------------------------------------

    public TestConditional(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestConditional.class);
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testHasNoArgConstructor() throws Exception {
        assertNotNull(new Conditional());
    }

    public void testUnaryMethods() {
        assertNotNull(Conditional.procedure(IsNull.instance(),NoOp.instance(),NoOp.instance()));   
        assertNotNull(Conditional.function(IsNull.instance(),Identity.instance(),Identity.instance()));   
        assertNotNull(Conditional.predicate(IsNull.instance(),Constant.truePredicate(),Constant.truePredicate()));   
    }

    public void testBinaryMethods() {
        assertNotNull(Conditional.procedure(IsGreaterThan.instance(),NoOp.instance(),NoOp.instance()));   
        assertNotNull(Conditional.function(IsGreaterThan.instance(),Max.instance(),Max.instance()));   
        assertNotNull(Conditional.predicate(IsGreaterThan.instance(),Constant.truePredicate(),Constant.truePredicate()));   
    }
}
