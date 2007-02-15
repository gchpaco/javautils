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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.IteratorToGeneratorAdapter;

/**
 * Generator for each element of a collection.
 *
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author  Jason Horman (jason@jhorman.org)
 */

public final class EachElement {
    public static final Generator from(Collection collection) {
        if(null == collection) {
            return null;
        } else {
            return EachElement.from(collection.iterator());
        }
    }

    public static final Generator from(Map map) {
        if(null == map) {
            return null;
        } else {
            return EachElement.from(map.entrySet().iterator());
        }
    }

    public static final Generator from(Object[] array) {
        if(null == array) {
            return null;
        } else {
            return EachElement.from(Arrays.asList(array).iterator());
        }
    }

    public static final Generator from(Iterator iter) {
        if(null == iter) {
            return null;
        } else {
            return new IteratorToGeneratorAdapter(iter);
        }
    }
}