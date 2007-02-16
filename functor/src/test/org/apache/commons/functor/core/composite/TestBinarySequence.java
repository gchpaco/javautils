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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.NoOp;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class TestBinarySequence extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestBinarySequence(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestBinarySequence.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BinarySequence<Object, Object>(new NoOp(),new NoOp());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testRunZero() throws Exception {
        BinarySequence<String, String> seq = new BinarySequence<String, String>();
        seq.run(null,null);
        seq.run("xyzzy","xyzzy");
    }

    public void testRunOne() throws Exception {
        RunCounter counter = new RunCounter();
        BinarySequence<String, String> seq = new BinarySequence<String, String>(counter);
        assertEquals(0,counter.count);
        seq.run(null,null);
        assertEquals(1,counter.count);
        seq.run("xyzzy","xyzzy");
        assertEquals(2,counter.count);
    }

    public void testRunTwo() throws Exception {
        RunCounter[] counter = { new RunCounter(), new RunCounter() };
        BinarySequence<Object, Object> seq = new BinarySequence<Object, Object>(counter[0],counter[1]);
        assertEquals(0,counter[0].count);
        assertEquals(0,counter[1].count);
        seq.run(null,null);
        assertEquals(1,counter[0].count);
        assertEquals(1,counter[1].count);
        seq.run("xyzzy","xyzzy");
        assertEquals(2,counter[0].count);
        assertEquals(2,counter[1].count);
    }
    
    public void testThen() throws Exception {
        List<RunCounter> list = new ArrayList<RunCounter>();
        BinarySequence<Object, Object> seq = new BinarySequence<Object, Object>();
        seq.run(null,null);        
        for(int i=0;i<10;i++) {
            RunCounter counter = new RunCounter();
            seq.then(counter);
            list.add(counter);
            seq.run("xyzzy","xyzzy");
            for(int j=0;j<list.size();j++) {
                assertEquals(list.size()-j,((list.get(j)).count));
            }
        }
    }
    
    public void testEquals() throws Exception {
        BinarySequence<?,?> p = new BinarySequence<Object,Object>();
        assertEquals(p,p);
        BinarySequence<?,?> q = new BinarySequence<Object,Object>();
        assertObjectsAreEqual(p,q);

        for(int i=0;i<3;i++) {
            p.then(new NoOp());
            assertObjectsAreNotEqual(p,q);
            q.then(new NoOp());
            assertObjectsAreEqual(p,q);
            p.then(new BinarySequence<Object,Object>(new NoOp(),new NoOp()));
            assertObjectsAreNotEqual(p,q);            
            q.then(new BinarySequence<Object,Object>(new NoOp(),new NoOp()));
            assertObjectsAreEqual(p,q);            
        }
                
        assertObjectsAreNotEqual(p,new NoOp());
    }

    // Classes
    // ------------------------------------------------------------------------
    
    static class RunCounter implements BinaryProcedure<Object,Object> {        
        public void run(Object a, Object b) {
            count++;    
        }        
        public int count = 0;
    }
}
