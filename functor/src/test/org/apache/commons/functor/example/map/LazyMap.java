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

import java.util.Map;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryFunction;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class LazyMap extends FunctoredMap {
    public LazyMap(Map map, final UnaryFunction factory) {
        super(map);
        setOnGet(new BinaryFunction() {
            public Object evaluate(Object m, Object key) {
                Map map = (Map)m;
                if(map.containsKey(key)) {
                    return map.get(key);
                } else {
                    Object value = factory.evaluate(key);                    
                    map.put(key,value);
                    return value;
                }
            }
        });
    }
}