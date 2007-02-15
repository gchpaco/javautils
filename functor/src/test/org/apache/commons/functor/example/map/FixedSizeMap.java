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
package org.apache.commons.functor.example.map;

import java.lang.reflect.Array;
import java.util.Map;

import org.apache.commons.functor.Algorithms;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.adapter.BinaryProcedureBinaryFunction;
import org.apache.commons.functor.core.composite.UnaryNot;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class FixedSizeMap extends FunctoredMap {
    public FixedSizeMap(Map map) {
        super(map);
        setOnPut(new BinaryFunction() {
            public Object evaluate(Object a, Object b) {
                Map map = (Map)a;
                Object key = Array.get(b,0);
                Object value = Array.get(b,1);
                if(map.containsKey(key)) {
                    return map.put(key,value);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        });
        
        setOnPutAll(new BinaryProcedure() {
            public void run(Object a, Object b) {
                Map dest = (Map)a;
                Map src = (Map)b;
                
                if(Algorithms.contains(src.keySet().iterator(),UnaryNot.not(new ContainsKey(dest)))) {
                    throw new IllegalArgumentException();
                } else {
                    dest.putAll(src);
                }
            }
        });
        
        setOnRemove(new BinaryProcedureBinaryFunction(new Throw(new UnsupportedOperationException())));
        setOnClear(new Throw(new UnsupportedOperationException()));
    }
}
