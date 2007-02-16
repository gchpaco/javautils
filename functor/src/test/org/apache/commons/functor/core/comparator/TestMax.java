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

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.functor.BaseFunctorTest;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestMax extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestMax(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestMax.class);
    }

    // Framework
    // ------------------------------------------------------------------------
    
    protected Object makeFunctor() {
        return new Max();
    }

    private Integer MIN = new Integer(Integer.MIN_VALUE); 
    private Integer MINUS_TWO = new Integer(-2); 
    private Integer ZERO = new Integer(0); 
    private Integer ONE = new Integer(1); 
    private Integer MAX = new Integer(Integer.MAX_VALUE); 
    // Tests
    // ------------------------------------------------------------------------
    
    public void testEvaluate() {
        Max f = new Max();
        assertEquals(ONE,f.evaluate(ONE,ONE));
        assertEquals(ONE,f.evaluate(ZERO,ONE));
        assertEquals(ONE,f.evaluate(ONE,ZERO));
        assertEquals(MAX,f.evaluate(ONE,MAX));
        assertEquals(MAX,f.evaluate(MIN,MAX));
        assertEquals(MINUS_TWO,f.evaluate(MIN,MINUS_TWO));
    }

    public void testEquals() {
        Max f = new Max();
        assertObjectsAreEqual(f,f);
        assertObjectsAreEqual(f,Max.instance());
        assertObjectsAreEqual(f,new Max(null));
        assertObjectsAreEqual(new Max(null),new Max(null));
        assertObjectsAreEqual(f,new Max(new ComparableComparator()));
        assertObjectsAreNotEqual(f,new Max(Collections.reverseOrder()));
    }
}
