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

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;

/**
 * Adapts a
 * {@link UnaryProcedure UnaryProcedure} 
 * to the 
 * {@link UnaryFunction UnaryFunction} interface
 * by always returning <code>null</code>.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying procedure is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class UnaryProcedureUnaryFunction implements UnaryFunction, Serializable {
    public UnaryProcedureUnaryFunction(UnaryProcedure procedure) {
        this.procedure = procedure;
    }
 
    public Object evaluate(Object obj) {
        procedure.run(obj);
        return null;
    }   

    public boolean equals(Object that) {
        if(that instanceof UnaryProcedureUnaryFunction) {
            return equals((UnaryProcedureUnaryFunction)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(UnaryProcedureUnaryFunction that) {
        return that == this || (null != that && (null == procedure ? null == that.procedure : procedure.equals(that.procedure)));
    }
    
    public int hashCode() {
        int hash = "UnaryProcedureUnaryFunction".hashCode();
        if(null != procedure) {
            hash ^= procedure.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "UnaryProcedureUnaryFunction<" + procedure + ">";
    }

    public static UnaryProcedureUnaryFunction adapt(UnaryProcedure procedure) {
        return null == procedure ? null : new UnaryProcedureUnaryFunction(procedure);
    }

    /** The {@link UnaryProcedure UnaryProcedure} I'm wrapping. */
    private UnaryProcedure procedure = null;
}
