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

import java.io.Serializable;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.BinaryProcedure;

/**
 * A {@link BinaryProcedure BinaryProcedure} 
 * similiar to Java's "ternary" 
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link BinaryPredicate predicate}
 * <i>p</i> and {@link BinaryProcedure procedures}
 * <i>q</i> and <i>r</i>, {@link #run runs}
 * <code>if(p.test(x,y)) { q.run(x,y); } else { r.run(x,y); }</code>.
 * <p>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class ConditionalBinaryProcedure implements BinaryProcedure, Serializable {

    // constructor
    // ------------------------------------------------------------------------

    public ConditionalBinaryProcedure(BinaryPredicate ifPred, BinaryProcedure thenPred, BinaryProcedure elsePred) {
        this.ifPred = ifPred;
        this.thenProc = thenPred;
        this.elseProc = elsePred;
    }
    
    // predicate interface
    // ------------------------------------------------------------------------
    public void run(Object left, Object right) {
        if(ifPred.test(left,right)) {
            thenProc.run(left,right);
        } else {
            elseProc.run(left,right);
        }
    }

    public boolean equals(Object that) {
        if(that instanceof ConditionalBinaryProcedure) {
            return equals((ConditionalBinaryProcedure)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(ConditionalBinaryProcedure that) {
        return null != that && 
                (null == ifPred ? null == that.ifPred : ifPred.equals(that.ifPred)) &&
                (null == thenProc ? null == that.thenProc : thenProc.equals(that.thenProc)) &&
                (null == elseProc ? null == that.elseProc : elseProc.equals(that.elseProc));
    }
    
    public int hashCode() {
        int hash = "ConditionalBinaryProcedure".hashCode();
        if(null != ifPred) {
            hash <<= 4;
            hash ^= ifPred.hashCode();            
        }
        if(null != thenProc) {
            hash <<= 4;
            hash ^= thenProc.hashCode();            
        }
        if(null != elseProc) {
            hash <<= 4;
            hash ^= elseProc.hashCode();            
        }
        return hash;
    }
    
    public String toString() {
        return "ConditionalBinaryProcedure<" + ifPred + "?" + thenProc + ":" + elseProc + ">";
    }

    // attributes
    // ------------------------------------------------------------------------
    private BinaryPredicate ifPred = null;
    private BinaryProcedure thenProc = null;
    private BinaryProcedure elseProc = null;
}
