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
import java.util.NoSuchElementException;

import org.apache.commons.functor.UnaryPredicate;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class FilteredIterator<T> implements Iterator<T> {

    // constructor
    // ------------------------------------------------------------------------
    
    public FilteredIterator(Iterator<T> iterator, UnaryPredicate<? super T> predicate) {
        if(null == iterator || null == predicate) {
            throw new NullPointerException();
        }
        this.predicate = predicate;
        this.iterator = iterator;
    }
    
    // iterator methods
    // ------------------------------------------------------------------------
    
    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        if(nextSet) {
            return true;
        }
        return setNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    public T next() {
        if(hasNext()) {            
            return returnNext();
        }
        throw new NoSuchElementException();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        if(canRemove) {
            canRemove = false;
            iterator.remove();
        } else {
            throw new IllegalStateException();
        }
    }
    

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FilteredIterator) {
            FilteredIterator<?> that = (FilteredIterator)obj;
            return predicate.equals(that.predicate) && iterator.equals(that.iterator);  
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = "FilteredIterator".hashCode();
        hash <<= 2;
        hash ^= predicate.hashCode();
        hash <<= 2;
        hash ^= iterator.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "FilteredIterator<" + iterator + "," + predicate + ">";
    }
    
    // class methods
    // ------------------------------------------------------------------------
    
    public static <T> Iterator<T> filter(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        return null == pred ? iter : (null == iter ? null : new FilteredIterator<T>(iter,pred));
    }
 
    // private
    // ------------------------------------------------------------------------
    
    private boolean setNext() {
        while(iterator.hasNext()) {
            canRemove = false;
            T obj = iterator.next();
            if(predicate.test(obj)) {
                next = obj;
                nextSet = true;
                return true;
            }
        }
        next = null;
        nextSet = false;
        return false;
    }
 
    private T returnNext() {
        T temp = next;
        canRemove = true;
        next = null;
        nextSet = false;
        return temp;
    }
 
    // attributes
    // ------------------------------------------------------------------------
    
    private UnaryPredicate<? super T> predicate = null;
    private Iterator<T> iterator = null;
    private T next = null;
    private boolean nextSet = false;
    private boolean canRemove = false;
    

}
