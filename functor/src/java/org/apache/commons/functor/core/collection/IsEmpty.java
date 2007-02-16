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
package org.apache.commons.functor.core.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.functor.UnaryPredicate;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IsEmpty implements UnaryPredicate<Object>, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    
    /**
   * 
   */
  private static final long serialVersionUID = 3750731793485471069L;

    public IsEmpty() { }

    // instance methods
    // ------------------------------------------------------------------------
    
    public boolean test(Object obj) {
        if(obj instanceof Collection) {
            return testCollection((Collection)obj);
        } else if(obj instanceof Map) {
            return testMap((Map)obj);
        } else if(obj instanceof String) {
            return testString((String)obj);
        } else if(null != obj && obj.getClass().isArray()) {
            return testArray(obj);
        } else if(null == obj){
            throw new NullPointerException("Argument must not be null");
        } else {
            throw new IllegalArgumentException("Expected Collection, Map, String or Array, found " + obj.getClass());
        } 
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object that) {
        return that instanceof IsEmpty;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return "IsEmpty".hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IsEmpty()";
    }

    private boolean testCollection(Collection<?> col) {
        return col.isEmpty();
    }
    
    private boolean testMap(Map<?, ?> map) {
        return map.isEmpty();
    }
    
    private boolean testString(String str) {
        return 0 == str.length();
    }
    
    private boolean testArray(Object array) {
        return 0 == Array.getLength(array);
    }

    // class methods
    // ------------------------------------------------------------------------
    
    public static final IsEmpty instance() {
        return INSTANCE;
    }
    
    // class variables
    // ------------------------------------------------------------------------
    
    private static final IsEmpty INSTANCE = new IsEmpty();

}
