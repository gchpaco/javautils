// ============================================================================
// $Id: TestEightRules.java,v 1.2 2006/12/16 16:48:57 davidahall Exp $
// Copyright (c) 2003-2006  David A. Hall
// ============================================================================
// The contents of this file are subject to the Common Development and
// Distribution License (CDDL), Version 1.0 (the License); you may not use this 
// file except in compliance with the License.  You should have received a copy
// of the the License along with this file: if not, a copy of the License is 
// available from Sun Microsystems, Inc.
//
// http://www.sun.com/cddl/cddl.html
//
// From time to time, the license steward (initially Sun Microsystems, Inc.) may
// publish revised and/or new versions of the License.  You may not use,  
// distribute, or otherwise make this file available under subsequent versions 
// of the License.
// 
// Alternatively, the contents of this file may be used under the terms of the 
// GNU Lesser General Public License Version 2.1 or later (the "LGPL"), in which
// case the provisions of the LGPL are applicable instead of those above. If you 
// wish to allow use of your version of this file only under the terms of the 
// LGPL, and not to allow others to use your version of this file under the 
// terms of the CDDL, indicate your decision by deleting the provisions above 
// and replace them with the notice and other provisions required by the LGPL. 
// If you do not delete the provisions above, a recipient may use your version 
// of this file under the terms of either the CDDL or the LGPL.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// ============================================================================

import java.text.Collator;
import java.util.*;
import junit.framework.TestCase;
import junit.swingui.TestRunner;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.adaptor.ConstantUnary;

public class TestEightRules extends TestCase {
    static public final String FOO = "_foo_";
    static public final String BAR = "_bar_";
    static public final String BAZ = "_baz_";
    static public final String QLX = "_qlx_";

    static public final ConstantUnary<String,Boolean> TRUE =
        new ConstantUnary<String,Boolean>(Boolean.TRUE);
    
    static public final ConstantUnary<String,Boolean> FALSE =
        new ConstantUnary<String,Boolean>(Boolean.FALSE);
        
    public TestEightRules(String name) { super(name); }

    public void testInRule() {
        assertFalse(EightRules.inRule().fn(BAR));
        assertFalse(EightRules.inRule(Arrays.asList()).fn(BAR));
        assertFalse(EightRules.inRule(FOO,BAZ,QLX).fn(BAR));
        assertFalse(EightRules.inRule(Arrays.asList(FOO,BAZ,QLX)).fn(BAR));
        assertTrue(EightRules.inRule(FOO,BAR).fn(BAR));
        assertTrue(EightRules.inRule(Arrays.asList(FOO,BAR)).fn(BAR));
        assertFalse(EightRules.inRule(FOO,BAR).fn(null));
        assertFalse(EightRules.inRule(Arrays.asList(FOO,BAR)).fn(null));
        assertFalse(EightRules.inRule().fn(null));
        assertFalse(EightRules.inRule(Arrays.asList()).fn(null));
        assertFalse(EightRules.inRule(new ArrayList<String>()).fn(null));
        assertTrue(EightRules.inRule((String)null).fn(null));
        assertTrue(EightRules.inRule(Arrays.asList((String)null)).fn(null));
    }

    public void testComparableRule() {
        UnaryFunctor<String,Boolean> ltFoo = EightRules.lessThan(FOO);
        assertTrue(ltFoo.fn(BAR));
        assertFalse(ltFoo.fn(FOO));
        assertFalse(ltFoo.fn(QLX));
        try {
            assertFalse(ltFoo.fn(null));
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
        
        UnaryFunctor<String,Boolean> leFoo = EightRules.lessThanOrEquals(FOO);
        assertTrue(leFoo.fn(BAR));
        assertTrue(leFoo.fn(FOO));
        assertFalse(leFoo.fn(QLX));
        try {
            leFoo.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
        
        UnaryFunctor<String,Boolean> gtFoo = EightRules.greaterThan(FOO);
        assertFalse(gtFoo.fn(BAR));
        assertFalse(gtFoo.fn(FOO));
        assertTrue(gtFoo.fn(QLX));
        try {
            gtFoo.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
        
        UnaryFunctor<String,Boolean> geFoo = EightRules.greaterThanOrEquals(FOO);
        assertFalse(geFoo.fn(BAR));
        assertTrue(geFoo.fn(FOO));
        assertTrue(geFoo.fn(QLX));
        try {
            geFoo.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
            
        UnaryFunctor<String,Boolean> eqFoo = EightRules.equals(FOO);
        assertFalse(eqFoo.fn(BAR));
        assertTrue(eqFoo.fn(FOO));
        assertFalse(eqFoo.fn(QLX));
        assertFalse(eqFoo.fn(null));
            
        UnaryFunctor<String,Boolean> neFoo = EightRules.notEquals(FOO);
        assertTrue(neFoo.fn(BAR));
        assertFalse(neFoo.fn(FOO));
        assertTrue(neFoo.fn(QLX));
        assertTrue(neFoo.fn(null));

        UnaryFunctor<String,Boolean> btwnBazQlx = EightRules.between(BAZ,QLX,false);
        assertFalse(btwnBazQlx.fn(BAR));
        assertFalse(btwnBazQlx.fn(BAZ));
        assertTrue(btwnBazQlx.fn(FOO));
        assertFalse(btwnBazQlx.fn(QLX));
        try {
            btwnBazQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}

        btwnBazQlx = EightRules.betweenExcludeBounds(BAZ,QLX);
        assertFalse(btwnBazQlx.fn(BAR));
        assertFalse(btwnBazQlx.fn(BAZ));
        assertTrue(btwnBazQlx.fn(FOO));
        assertFalse(btwnBazQlx.fn(QLX));
        try {
            btwnBazQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}

        UnaryFunctor<String,Boolean> fromBazToQlx = EightRules.between(BAZ,QLX,true);
        assertFalse(fromBazToQlx.fn(BAR));
        assertTrue(fromBazToQlx.fn(BAZ));
        assertTrue(fromBazToQlx.fn(FOO));
        assertTrue(fromBazToQlx.fn(QLX));
        try {
            fromBazToQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
        
        fromBazToQlx = EightRules.between(BAZ,QLX);
        assertFalse(fromBazToQlx.fn(BAR));
        assertTrue(fromBazToQlx.fn(BAZ));
        assertTrue(fromBazToQlx.fn(FOO));
        assertTrue(fromBazToQlx.fn(QLX));
        try {
            fromBazToQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
    }
    

    public void testComparatorRule() {
        //Get the Collator for US English and set its strength to PRIMARY
        Collator usCollator = Collator.getInstance(Locale.US);
        usCollator.setStrength(Collator.PRIMARY);

        String foo = FOO.toUpperCase();
        
        UnaryFunctor<String,Boolean> ltFoo = EightRules.lessThan(foo, usCollator);
        assertTrue(ltFoo.fn(BAR));
        assertFalse(ltFoo.fn(FOO));
        assertFalse(ltFoo.fn(QLX));
        
        UnaryFunctor<String,Boolean> leFoo = EightRules.lessThanOrEquals(foo, usCollator);
        assertTrue(leFoo.fn(BAR));
        assertTrue(leFoo.fn(FOO));
        assertFalse(leFoo.fn(QLX));
        
        UnaryFunctor<String,Boolean> gtFoo = EightRules.greaterThan(foo, usCollator);
        assertFalse(gtFoo.fn(BAR));
        assertFalse(gtFoo.fn(FOO));
        assertTrue(gtFoo.fn(QLX));
        
        UnaryFunctor<String,Boolean> geFoo = EightRules.greaterThanOrEquals(foo, usCollator);
        assertFalse(geFoo.fn(BAR));
        assertTrue(geFoo.fn(FOO));
        assertTrue(geFoo.fn(QLX));
        
        UnaryFunctor<String,Boolean> eqFoo = EightRules.equals(foo, usCollator);
        assertFalse(eqFoo.fn(BAR));
        assertTrue(eqFoo.fn(FOO));
        assertFalse(eqFoo.fn(QLX));
        
        UnaryFunctor<String,Boolean> neFoo = EightRules.notEquals(foo, usCollator);
        assertTrue(neFoo.fn(BAR));
        assertFalse(neFoo.fn(FOO));
        assertTrue(neFoo.fn(QLX));

        UnaryFunctor<String,Boolean> btwnBazQlx = EightRules.between(BAZ,QLX,usCollator,false);
        assertFalse(btwnBazQlx.fn(BAR));
        assertFalse(btwnBazQlx.fn(BAZ));
        assertTrue(btwnBazQlx.fn(FOO));
        assertFalse(btwnBazQlx.fn(QLX));
        try {
            btwnBazQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}

        btwnBazQlx = EightRules.betweenExcludeBounds(BAZ,QLX,usCollator);
        assertFalse(btwnBazQlx.fn(BAR));
        assertFalse(btwnBazQlx.fn(BAZ));
        assertTrue(btwnBazQlx.fn(FOO));
        assertFalse(btwnBazQlx.fn(QLX));
        try {
            btwnBazQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}

        UnaryFunctor<String,Boolean> fromBazToQlx = EightRules.between(BAZ,QLX,usCollator,true);
        assertFalse(fromBazToQlx.fn(BAR));
        assertTrue(fromBazToQlx.fn(BAZ));
        assertTrue(fromBazToQlx.fn(FOO));
        assertTrue(fromBazToQlx.fn(QLX));
        try {
            fromBazToQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
        
        fromBazToQlx = EightRules.between(BAZ,QLX, usCollator);
        assertFalse(fromBazToQlx.fn(BAR));
        assertTrue(fromBazToQlx.fn(BAZ));
        assertTrue(fromBazToQlx.fn(FOO));
        assertTrue(fromBazToQlx.fn(QLX));
        try {
            fromBazToQlx.fn(null);
            fail("Expecting null pointer when comparing String to null");
        }
        catch (NullPointerException x) {}
    }

    public void testAndRule() {
        assertTrue(EightRules.and(TRUE,TRUE).fn(FOO));
        assertFalse(EightRules.and(TRUE,FALSE).fn(FOO));
        assertFalse(EightRules.and(FALSE,TRUE).fn(FOO));
        assertFalse(EightRules.and(FALSE,FALSE).fn(FOO));
        
        try {
            assertFalse(EightRules.and(FALSE,null).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
            
        try {
            assertFalse(EightRules.and(null,FALSE).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
    }

    public void testOrRule() {
        assertTrue(EightRules.or(TRUE,TRUE).fn(FOO));
        assertTrue(EightRules.or(TRUE,FALSE).fn(FOO));
        assertTrue(EightRules.or(FALSE,TRUE).fn(FOO));
        assertFalse(EightRules.or(FALSE,FALSE).fn(FOO));

        try {
            assertTrue(EightRules.or(TRUE,null).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
            
        try {
            assertTrue(EightRules.or(null,TRUE).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
    }

    public void testXorRule() {
        assertFalse(EightRules.xor(TRUE,TRUE).fn(FOO));
        assertTrue(EightRules.xor(TRUE,FALSE).fn(FOO));
        assertTrue(EightRules.xor(FALSE,TRUE).fn(FOO));
        assertFalse(EightRules.xor(FALSE,FALSE).fn(FOO));

        try {
            assertTrue(EightRules.xor(TRUE,null).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
            
        try {
            assertTrue(EightRules.xor(null,TRUE).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
    }

    public void testNotRule() {
        assertFalse(EightRules.not(TRUE).fn(FOO));
        assertTrue(EightRules.not(FALSE).fn(FOO));

        try {
            assertTrue(EightRules.not(null).fn(FOO));
            fail("expecting IllegalArgumentException");
        }
        catch (IllegalArgumentException x) {}
    }

    public void testRegexRule() {
        String inUnderbars = "^_.*_$";
        String startsWith_b = "^_b.*";
        String contains_x = ".*x.*";

        UnaryFunctor<String,Boolean> matchesAll =
            EightRules.<String>matchAll(inUnderbars, startsWith_b);
        assertTrue(matchesAll.fn(BAR));
        assertTrue(matchesAll.fn(BAZ));
        assertFalse(matchesAll.fn(FOO));
        assertFalse(matchesAll.fn(QLX));
        
        UnaryFunctor<String,Boolean> matchesAny =
            EightRules.<String>matchAny(startsWith_b, contains_x);
        assertTrue(matchesAny.fn(BAR));
        assertTrue(matchesAny.fn(BAZ));
        assertFalse(matchesAny.fn(FOO));
        assertTrue(matchesAny.fn(QLX));
        
        UnaryFunctor<String,Boolean> matchesNone =
            EightRules.<String>matchNone(startsWith_b, contains_x);
        assertFalse(matchesNone.fn(BAR));
        assertFalse(matchesNone.fn(BAZ));
        assertTrue(matchesNone.fn(FOO));
        assertFalse(matchesNone.fn(QLX));
    }
    

    static public void main (String[] args) {
        junit.swingui.TestRunner.run(TestEightRules.class);
    }
}
