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
package org.apache.commons.functor.adapter;

import java.io.Serializable;

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.UnaryProcedure;

/**
 * Adapts a
 * {@link UnaryProcedure UnaryProcedure} 
 * to the 
 * {@link BinaryProcedure BinaryProcedure} interface 
 * by ignoring the second binary argument.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying functor is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IgnoreRightProcedure<T,U> implements BinaryProcedure<T,U>, Serializable {
    public IgnoreRightProcedure(UnaryProcedure<T> procedure) {
        this.procedure = procedure;
    }
 
    public void run(T left, U right) {
        procedure.run(left);
    }   

    public boolean equals(Object that) {
        if(that instanceof IgnoreRightProcedure) {
            return equals((IgnoreRightProcedure)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(IgnoreRightProcedure that) {
        return that == this || (null != that && (null == procedure ? null == that.procedure : procedure.equals(that.procedure)));
    }
    
    public int hashCode() {
        int hash = "IgnoreRightProcedure".hashCode();
        if(null != procedure) {
            hash ^= procedure.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "IgnoreRightProcedure<" + procedure + ">";
    }

    public static <T,U> IgnoreRightProcedure<T,U> adapt(UnaryProcedure<T> procedure) {
        return null == procedure ? null : new IgnoreRightProcedure<T,U>(procedure);
    }

    /** The {@link UnaryProcedure UnaryProcedure} I'm wrapping. */
    private UnaryProcedure<T> procedure = null;
}
