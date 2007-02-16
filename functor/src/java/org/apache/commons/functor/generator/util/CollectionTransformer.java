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

package org.apache.commons.functor.generator.util;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.Generator;

/**
 * Transforms a generator into a collection. If a collection is not passed into
 * the constructor an ArrayList will be returned from the transform method.
 *
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Jason Horman (jason@jhorman.org)
 */

public class CollectionTransformer<T> implements UnaryFunction<Generator<? extends T>,Collection<? super T>> {

	// instance methods
	//---------------------------------------------------
    private Collection<? super T> toFill = null;

	// constructors
	//---------------------------------------------------
    public CollectionTransformer() {
        toFill = new ArrayList<T>();
    }

    public CollectionTransformer(Collection<? super T> toFill) {
        this.toFill = toFill;
    }

	// instance methods
	//---------------------------------------------------
	@SuppressWarnings("cast")
  public Collection<? super T> evaluate(Object obj) {
		return evaluate((Generator<?>)obj);
	}

    public Collection<? super T> evaluate(Generator<? extends T> generator) {
        generator.run(new UnaryProcedure<T>() {
            public void run(T obj) {
                toFill.add(obj);
            }
        });

        return toFill;
    }
}