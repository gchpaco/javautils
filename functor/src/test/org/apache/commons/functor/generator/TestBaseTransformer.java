/*
 * Copyright 2003-2004 The Apache Software Foundation.
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

package org.apache.commons.functor.generator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 * @deprecated BaseTransformer is going to be removed.
 */
@Deprecated
public class TestBaseTransformer extends TestCase {

    // Conventional
    // ------------------------------------------------------------------------

    public TestBaseTransformer(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestBaseTransformer.class);
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    // Tests
    // ------------------------------------------------------------------------

    public void testEvaluateDelegatesToTransform() {
        Transformer<Object, Integer> t = new MockTransformer<Object>();
        assertEquals(new Integer(1),t.evaluate(null));
        assertEquals(new Integer(2),t.evaluate(null));
        assertEquals(new Integer(3),t.evaluate(null));
    }
    
    // Classes
    // ------------------------------------------------------------------------

    @Deprecated
    static class MockTransformer<T> extends BaseTransformer<T,Integer> {
        @Override
        public Integer transform(Generator<T> gen) {
            return new Integer(++timesCalled);
        }
        
        public int timesCalled = 0;
    }
}