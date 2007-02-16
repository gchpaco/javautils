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

package org.apache.commons.functor.core;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.UnaryPredicate;

/**
 * A predicate that returns <code>false</code>
 * the first <i>n</i> times it is invoked, and
 * <code>true</code> thereafter. 
 *
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Jason Horman (jason@jhorman.org)
 * @author Rodney Waldhoff
 */

public final class Offset implements Predicate, UnaryPredicate<Object>, BinaryPredicate<Object,Object> {

    public Offset(int count) {
        if(count < 0) { 
            throw new IllegalArgumentException("Argument must be a non-negative integer.");
        }
        this.min = count;
    }

    public boolean test() {
        // stop incremeting when we've hit max, so we don't loop around
        if(current < min) {
            current++;
            return false;
        }
        return true;
    }

    public boolean test(Object obj) {
        return test();        
    }

    public boolean test(Object a, Object b) {
        return test();        
    }

    @Override
    public String toString() {
        return "Offset<" + min + ">";
    }
    // instance variables
    //---------------------------------------------------------------
    private int min;
    private int current = 0;

}