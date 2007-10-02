// ============================================================================
// $Id: ClasspathSuite.java,v 1.4 2005/08/02 23:45:23 davidahall Exp $
// Copyright (c) 2003-2005  David A. Hall
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
package tools;

import java.util.Enumeration;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.SimpleTestCollector;
import junit.runner.TestCollector;


/**
 * ClasspathSuite.java
 *
 * @author <a href="mailto:davidahall@users.sf.net">David A. Hall</a>
 * @version
 */

public class ClasspathSuite extends TestSuite {
    
	static private final String TESTCOLLECTOR_KEY= "TestCollectorClass";
    
    static public TestSuite suite() {
        TestSuite suite = new TestSuite();
        TestCollector collector = createTestCollector(); // see TestRunner
        Enumeration tests = collector.collectTests();
        while(tests.hasMoreElements()) {
            String classname = (String) tests.nextElement();
            try {
                suite.addTestSuite(Class.forName(classname));
            }
            catch (ClassNotFoundException x) { x.printStackTrace(); }
        }
        
        return suite;        
    }

	static TestCollector createTestCollector() {
        String className= BaseTestRunner.getPreference(TESTCOLLECTOR_KEY);
		if (className != null) {			
			Class collectorClass= null;
			try {
				collectorClass= Class.forName(className);
				return (TestCollector)collectorClass.newInstance();
			} catch(Exception e) {
				System.out.println("Could not create TestCollector - using default collector");
                e.printStackTrace();
			}
		}
		return new SimpleTestCollector();
	}

    public void testBogus() {}
}
