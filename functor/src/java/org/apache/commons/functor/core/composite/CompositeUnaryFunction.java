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

import org.apache.commons.functor.UnaryFunction;

/**
 * A {@link UnaryFunction UnaryFunction} 
 * representing the composition of 
 * {@link UnaryFunction UnaryFunctions},
 * "chaining" the output of one to the input
 * of another.  For example, 
 * <pre>new CompositeUnaryFunction(f).of(g)</code>
 * {@link #evaluate evaluates} to 
 * <code>f.evaluate(g.evaluate(obj))</code>, and
 * <pre>new CompositeUnaryFunction(f).of(g).of(h)</pre>
 * {@link #evaluate evaluates} to 
 * <code>f.evaluate(g.evaluate(h.evaluate(obj)))</code>.
 * <p>
 * When the collection is empty, this function is 
 * an identity function.
 * </p>
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
public class CompositeUnaryFunction<T,U> implements UnaryFunction<T,U>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 241333691274674779L;


    // constructor
    // ------------------------------------------------------------------------
    public CompositeUnaryFunction() {
    }

    public CompositeUnaryFunction(UnaryFunction<? super T, ? extends U> f) {
      list.add (f);
    }

    public <V> CompositeUnaryFunction(UnaryFunction<? super T, ? extends V> f, UnaryFunction<? super V, ? extends U> g) {
      list.add (f);
      list.add (g);
    }

    // modifiers
    // ------------------------------------------------------------------------ 
    @SuppressWarnings("unchecked")
    public <V> CompositeUnaryFunction<T,V> of(UnaryFunction<? super U, ? extends V> f) {
        list.add(f);
        return (CompositeUnaryFunction<T, V>) this;
    }
 
    // predicate interface
    // ------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public U evaluate(T obj) {        
        Object result = obj;
        for(ListIterator<UnaryFunction<?,?>> iter = list.listIterator(list.size()); iter.hasPrevious();) {
            result = ((UnaryFunction)iter.previous()).evaluate(result);
        }
        return (U) result;
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof CompositeUnaryFunction) {
            return equals((CompositeUnaryFunction)that);
        }
        return false;
    }
    
    public boolean equals(CompositeUnaryFunction<?, ?> that) {
        // by construction, list is never null
        return null != that && list.equals(that.list);
    }
    
    @Override
    public int hashCode() {
        // by construction, list is never null
        return "CompositeUnaryFunction".hashCode() ^ list.hashCode();
    }
    
    @Override
    public String toString() {
        return "CompositeUnaryFunction<" + list + ">";
    }
    
    
    // attributes
    // ------------------------------------------------------------------------
    private List<UnaryFunction<?,?>> list = new ArrayList<UnaryFunction<?, ?>>();

}
