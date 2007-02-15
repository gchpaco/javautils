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

import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestComparatorFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestComparatorFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestComparatorFunction.class);
    }

    // Framework
    // ------------------------------------------------------------------------
    
    protected Object makeFunctor() {
        return new ComparatorFunction(new ComparableComparator());
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testEvaluate() {
        ComparatorFunction f = new ComparatorFunction();

        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MAX_VALUE),new Integer(Integer.MAX_VALUE)))).intValue() == 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MAX_VALUE),new Integer(1)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MAX_VALUE),new Integer(0)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MAX_VALUE),new Integer(-1)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MAX_VALUE),new Integer(Integer.MIN_VALUE)))).intValue() > 0);

        assertTrue(((Integer)(f.evaluate(new Integer(1),new Integer(Integer.MAX_VALUE)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(1),new Integer(1)))).intValue() == 0);
        assertTrue(((Integer)(f.evaluate(new Integer(1),new Integer(0)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(1),new Integer(-1)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(1),new Integer(Integer.MIN_VALUE)))).intValue() > 0);
        
        assertTrue(((Integer)(f.evaluate(new Integer(0),new Integer(Integer.MAX_VALUE)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(0),new Integer(1)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(0),new Integer(0)))).intValue() == 0);
        assertTrue(((Integer)(f.evaluate(new Integer(0),new Integer(-1)))).intValue() > 0);
        assertTrue(((Integer)(f.evaluate(new Integer(0),new Integer(Integer.MIN_VALUE)))).intValue() > 0);
        
        assertTrue(((Integer)(f.evaluate(new Integer(-1),new Integer(Integer.MAX_VALUE)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(-1),new Integer(1)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(-1),new Integer(0)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(-1),new Integer(-1)))).intValue() == 0);
        assertTrue(((Integer)(f.evaluate(new Integer(-1),new Integer(Integer.MIN_VALUE)))).intValue() > 0);

        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MIN_VALUE),new Integer(Integer.MAX_VALUE)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MIN_VALUE),new Integer(1)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MIN_VALUE),new Integer(0)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MIN_VALUE),new Integer(-1)))).intValue() < 0);
        assertTrue(((Integer)(f.evaluate(new Integer(Integer.MIN_VALUE),new Integer(Integer.MIN_VALUE)))).intValue() == 0);
    }

    public void testEquals() {
        ComparatorFunction f = new ComparatorFunction();
        assertObjectsAreEqual(f,f);
        assertObjectsAreEqual(f,new ComparatorFunction(null));
        assertObjectsAreEqual(new ComparatorFunction(null),new ComparatorFunction(null));
        assertObjectsAreEqual(f,new ComparatorFunction(new ComparableComparator()));
        assertObjectsAreNotEqual(f,new ComparatorFunction(Collections.reverseOrder()));
    }
}
