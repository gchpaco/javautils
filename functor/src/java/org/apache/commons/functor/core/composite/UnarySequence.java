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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.functor.UnaryProcedure;

/**
 * A {@link UnaryProcedure UnaryProcedure} 
 * that {@link UnaryProcedure#run runs} an ordered 
 * sequence of {@link UnaryProcedure UnaryProcedures}.
 * When the sequence is empty, this procedure is does
 * nothing.
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
public class UnarySequence<T> implements UnaryProcedure<T>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 9182944988148717317L;


    // constructor
    // ------------------------------------------------------------------------
    public UnarySequence() {
    }

    public UnarySequence(UnaryProcedure<? super T> p) {
        then(p);
    }

    public UnarySequence(UnaryProcedure<? super T> p, UnaryProcedure<? super T> q) {
        then(p);
        then(q);
    }

    // modifiers
    // ------------------------------------------------------------------------ 
    public UnarySequence<T> then(UnaryProcedure<? super T> p) {
        list.add(p);
        return this;
    }
 
    // predicate interface
    // ------------------------------------------------------------------------
    public void run(T obj) {        
        for(ListIterator<UnaryProcedure<? super T>> iter = list.listIterator(list.size()); iter.hasPrevious();) {
            iter.previous().run(obj);
        }
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof UnarySequence) {
            return equals((UnarySequence)that);
        }
        return false;
    }
    
    public boolean equals(UnarySequence<?> that) {
        // by construction, list is never null
        return null != that && list.equals(that.list);
    }
    
    @Override
    public int hashCode() {
        // by construction, list is never null
        return "UnarySequence".hashCode() ^ list.hashCode();
    }
    
    @Override
    public String toString() {
        return "UnarySequence<" + list + ">";
    }
    
    
    // attributes
    // ------------------------------------------------------------------------
    private List<UnaryProcedure<? super T>> list = new ArrayList<UnaryProcedure<? super T>>();

}