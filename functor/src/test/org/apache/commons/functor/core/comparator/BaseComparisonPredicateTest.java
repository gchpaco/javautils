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
package org.apache.commons.functor.core.comparator;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public abstract class BaseComparisonPredicateTest extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public BaseComparisonPredicateTest(String testName) {
        super(testName);
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
    
    public final void testTestNull() throws Exception {
        BinaryPredicate p = (BinaryPredicate)(makeFunctor());
        try {
            p.test(new Integer(2),null);
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            p.test(null,new Integer(2));
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            p.test(null,null);
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }
    
    public final void testTestNonComparable() throws Exception {
        BinaryPredicate p = (BinaryPredicate)(makeFunctor());
        try {
            p.test(new Integer(2),new Object());
            fail("Expected ClassCastException");
        } catch(ClassCastException e) {
            // expected
        }
        try {
            p.test(new Object(),new Integer(2));
            fail("Expected ClassCastException");
        } catch(ClassCastException e) {
            // expected
        }
        try {
            p.test(new Object(),new Object());
            fail("Expected ClassCastException");
        } catch(ClassCastException e) {
            // expected
        }
    }
        
}
