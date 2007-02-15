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

import java.io.Serializable;

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.UnaryProcedure;

/**
 * A procedure that does nothing at all.
 * <p>
 * Note that this class implements {@link Procedure},
 * {@link UnaryProcedure}, and {@link BinaryProcedure}.
 * </p>   
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class NoOp implements Procedure, UnaryProcedure, BinaryProcedure, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    public NoOp() {
    }
 
    // predicate interface
    // ------------------------------------------------------------------------
    public void run() {
    }

    public void run(Object obj) {
    }

    public void run(Object left, Object right) {
    }

    public boolean equals(Object that) {
        return (that instanceof NoOp);
    }
        
    public int hashCode() {
        return "NoOp".hashCode();
    }
    
    public String toString() {
        return "NoOp";
    }
    
    // static methods
    // ------------------------------------------------------------------------
    public static NoOp instance() {
        return INSTANCE;
    }

    // static attributes
    // ------------------------------------------------------------------------
    private static final NoOp INSTANCE = new NoOp();
}
