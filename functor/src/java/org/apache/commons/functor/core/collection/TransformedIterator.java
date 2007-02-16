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

import java.util.Iterator;

import org.apache.commons.functor.UnaryFunction;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class TransformedIterator<T,U> implements Iterator<U> {

    // constructor
    // ------------------------------------------------------------------------
    
    public TransformedIterator(Iterator<T> iterator, UnaryFunction<? super T,? extends U> function) {
        if(null == iterator || null == function) {
            throw new NullPointerException();
        }
        this.function = function;
        this.iterator = iterator;
    }
    
    // iterator methods
    // ------------------------------------------------------------------------
    
    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    public U next() {
        return function.evaluate(iterator.next());
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        iterator.remove();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TransformedIterator) {
            TransformedIterator<?,?> that = (TransformedIterator)obj;
            return function.equals(that.function) && iterator.equals(that.iterator);  
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = "TransformedIterator".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        hash <<= 2;
        hash ^= iterator.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "TransformedIterator<" + iterator + "," + function + ">";
    }
    
    // class methods
    // ------------------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
    public static <T,U> Iterator<U> transform(Iterator<T> iter, UnaryFunction<? super T,? extends U> func) {
      if (func == null) return (Iterator<U>) iter;
      if (iter == null) return null;
      return new TransformedIterator<T,U>(iter,func);
    }
 
 
    // attributes
    // ------------------------------------------------------------------------
    
    private UnaryFunction<? super T,? extends U> function = null;
    private Iterator<T> iterator = null;
    

}
