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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;

/**
 * {@link #evaluate Evaluates} to constant value.
 * <p>
 * {@link #test Tests} to a constant value, assuming
 * a boolean of Boolean value is supplied.
 *
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * constant <code>Object</code> is.  Attempts to serialize
 * an instance whose value is not 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class Constant<T> implements Function<T>, UnaryFunction<Object,T>, BinaryFunction<Object,Object,T>, Predicate, UnaryPredicate<Object>, BinaryPredicate<Object,Object>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = -4347268151123086127L;

    public Constant(T value) {
        this.value = value;
    }
 
    // function interface
    // ------------------------------------------------------------------------
    public T evaluate() {
        return value;
    }

    public T evaluate(Object obj) {
        return evaluate();
    }

    public T evaluate(Object left, Object right) {
        return evaluate();
    }

    public boolean test() {
        return ((Boolean)evaluate()).booleanValue();
    }

    public boolean test(Object obj) {
        return test();
    }

    public boolean test(Object left, Object right) {
        return test();
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof Constant) {
            return equals((Constant<?>)that);
        }
        return false;
    }
    
    public boolean equals(Constant<?> that) {
        return (null != that && (null == this.value ? null == that.value : this.value.equals(that.value)));
    }
    
    @Override
    public int hashCode() {
        int hash = "Constant".hashCode();
        if(null != value) {
            hash ^= value.hashCode();
        }
        return hash;
    }
    
    @Override
    public String toString() {
        return "Constant<" + String.valueOf(value) + ">";
    }
    
    // attributes
    // ------------------------------------------------------------------------
    private T value;

    // static methods
    // ------------------------------------------------------------------------
    
    /** 
     * Get a <code>Constant</code> that always
     * returns <code>true</code>
     * @return a <code>Constant</code> that always
     *         returns <code>true</code>
     */
    public static Constant<Boolean> truePredicate() {
        return TRUE_PREDICATE;
    }

    /** 
     * Get a <code>Constant</code> that always
     * returns <code>false</code>
     * @return a <code>Constant</code> that always
     *         returns <code>false</code>
     */
    public static Constant<Boolean> falsePredicate() {
        return FALSE_PREDICATE;
    }
    
    /** 
     * Get a <code>Constant</code> that always
     * returns <i>value</i>
     * @param value the constant value
     * @return a <code>Constant</code> that always
     *         returns <i>value</i>
     */
    public static Constant<Boolean> predicate(boolean value) {
        return value ? TRUE_PREDICATE : FALSE_PREDICATE;
    }

    public static <T> Constant<T> instance(T value) {
        return new Constant<T>(value);
    }
    
    // static attributes
    // ------------------------------------------------------------------------
    private static final Constant<Boolean> TRUE_PREDICATE = new Constant<Boolean>(true);
    private static final Constant<Boolean> FALSE_PREDICATE = new Constant<Boolean>(false);
}
