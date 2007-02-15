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

import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;


/**
 * A {@link Procedure} implementation of a while loop. Given a {@link Predicate} 
 * <i>c</i> and an {@link Procedure} <i>p</i>, {@link #run runs}
 * <code>do { p.run(); } while(c.test())</code>.
 * <p>
 * Note that although this class implements 
 * {@link java.io.Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Herve Quiroz
 * @author Rodney Waldhoff
 */
public class DoWhileProcedure extends AbstractLoopProcedure {
    public DoWhileProcedure(Procedure action, Predicate condition) {
        super(condition, action);
    }

    public void run() {
        do {
            getAction().run();
        } while(getCondition().test());
    }


    public boolean equals(Object object) {
        if (object instanceof DoWhileProcedure) {
        	return super.equals(object);
        } else {
        	return false;
        }
    }

    public int hashCode() {
    	return super.hashCode("DoWhileProcedure".hashCode());
    }

    public String toString() {
        return "DoWhileProcedure<do("+getAction()+") while("+getCondition()+")>";
    }
}
