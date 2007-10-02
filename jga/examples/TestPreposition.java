// ============================================================================
// $Id: TestPreposition.java,v 1.2 2006/12/16 16:48:57 davidahall Exp $
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.adaptor.OrUnary;
import net.sf.jga.fn.property.CompareProperty;
import net.sf.jga.parser.FunctorParser;
import net.sf.jga.parser.ParseException;
import net.sf.jga.util.Iterables;

public class TestPreposition extends TestCase {

    public TestPreposition(String name) { super(name); }
    public TestPreposition() { super(); }

    public void testPreposition() throws ParseException {
        
        FunctorParser parser = new FunctorParser();
        
        List<Student> honorRoll = new ArrayList<Student>();

        // UnaryFunctor<Student,Boolean> isPassing =
        //     parser.parseUnary("x.getGrade() == Grade.A || x.getGrade() == Grade.B", Student.class);

        // Another form, built directly
            
        UnaryFunctor<Student,Boolean> isPassing =
            new OrUnary(new CompareProperty<Student,Grade>(Student.class, "Grade", Grade.A),
                        new CompareProperty<Student,Grade>(Student.class, "Grade", Grade.B));


        UnaryFunctor<Student,Boolean> isNotAbsent =
            new CompareProperty<Student,Integer>(Student.class, "DaysAbsent", 0);

        // Another form, built via the parser
            
        // UnaryFunctor<Student,Boolean> isNotAbsent =
        //     parser.parseUnary("x.getDaysAbsent() == 0", Student.class);
            

        UnaryFunctor<Student,?> addToHonorRoll =
            parser.parseBinary("x.add(y)", List.class, Student.class).bind1st(honorRoll);

        // Another form, built directly
        
        // UnaryFunctor addToHonorRoll =
        //     new InvokeMethod("add", List.class, new Class[]{ String.class })
        //         .bind1st(honorRoll).compose(new ArrayUnary());
            
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        
        //      Alex's tests begin here: much of this is Alex's code, reproduced
        //      to emphasize that the implementations are functionally equivalent.
        
        Student student = new Student(Grade.D, 0);
        
        Preposition.when(student, isNotAbsent, addToHonorRoll);
        
        assertEquals(1, honorRoll.size());
        assertEquals(0, honorRoll.get(0).getDaysAbsent());
        honorRoll.clear();
        
        Student[] studentArray = new Student[] {
            new Student(Grade.A, 1), new Student(Grade.B, 0),
            new Student(Grade.C, 0) };
        Preposition.foreach(studentArray, isPassing, addToHonorRoll);
        assertEquals(2, honorRoll.size());
        assertEquals(Grade.A, honorRoll.get(0).getGrade());
        assertEquals(Grade.B, honorRoll.get(1).getGrade());
        honorRoll.clear();
        
        List<Student> students = Arrays.asList(studentArray);
        Preposition.foreach(students, isPassing, addToHonorRoll);
        assertEquals(2, honorRoll.size());
        assertEquals(Grade.A, honorRoll.get(0).getGrade());
        assertEquals(Grade.B, honorRoll.get(1).getGrade());
        honorRoll.clear();

        // Tests Neil's syntax
        Preposition.foreach(students).where(isPassing).doo(addToHonorRoll);
        assertEquals(2, honorRoll.size());
        assertEquals(Grade.A, honorRoll.get(0).getGrade());
        assertEquals(Grade.B, honorRoll.get(1).getGrade());
        honorRoll.clear();

        // Test jga's syntax
        Iterables.addAll(honorRoll, Iterables.filter(students, isPassing));
        assertEquals(2, honorRoll.size());
        assertEquals(Grade.A, honorRoll.get(0).getGrade());
        assertEquals(Grade.B, honorRoll.get(1).getGrade());
        honorRoll.clear();
    }

    static enum Grade { A, B, C, D, F }
    
    static public class Student {
        private Grade grade;
        private int daysAbsent;
        public Student(Grade grade, int daysAbsent) {
            this.grade = grade;
            this.daysAbsent = daysAbsent;
        }
        public Grade getGrade() { return grade; }
        public int getDaysAbsent() { return daysAbsent; }
    }


    static public void main (String[] args) {
        junit.swingui.TestRunner.run(TestPreposition.class);
    }
}


    
