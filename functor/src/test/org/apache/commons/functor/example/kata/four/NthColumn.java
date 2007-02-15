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
package org.apache.commons.functor.example.kata.four;

import java.util.StringTokenizer;

import org.apache.commons.functor.UnaryFunction;

/**
 * Evaluates the input String to extrace the nth whitespace
 * delmited column.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class NthColumn implements UnaryFunction {
    public NthColumn(int n) {
        this.n = n;
    }
    
    public Object evaluate(Object obj) {
        StringTokenizer toker = new StringTokenizer((String)obj);
        for(int count = 0; count < n && toker.hasMoreTokens();count++) {
            toker.nextToken();
        }
        return toker.hasMoreTokens() ? toker.nextToken() : null;
    }

    private final int n;

    public static final NthColumn instance(int n) {
        return new NthColumn(n);
    }
}