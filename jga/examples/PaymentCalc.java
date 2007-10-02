// ============================================================================
// $Id: PaymentCalc.java,v 1.6 2005/08/02 23:45:05 davidahall Exp $
// Copyright (c) 2004-2005  David A. Hall
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

/**
 * This example shows a simple spreadsheet and a not-so-simple formula.  The
 * user can enter values for the loan amount, interest rate, and number of
 * payments, and the resulting payment amount will be updated on each change.
 * <p>
 * Copyright &copy; 2004-2005  David A. Hall
 * @author <a href="mailto:davidahall@users.sf.net">David A. Hall</a>
 */

import java.text.NumberFormat;
import javax.swing.JFrame;
import net.sf.jga.fn.BinaryFunctor;
import net.sf.jga.fn.Generator;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.adaptor.Constant;
import net.sf.jga.fn.arithmetic.Divides;
import net.sf.jga.fn.arithmetic.Minus;
import net.sf.jga.fn.arithmetic.Multiplies;
import net.sf.jga.fn.arithmetic.Negate;
import net.sf.jga.fn.arithmetic.Plus;
import net.sf.jga.fn.property.ArrayBinary;
import net.sf.jga.fn.property.InvokeMethod;
import net.sf.jga.fn.string.FormatValue;
import net.sf.jga.fn.string.ParseFormat;
import net.sf.jga.swing.Spreadsheet;

public class PaymentCalc {
    static public void main(String[] args) {
        Spreadsheet paymentCalc = new Spreadsheet(4,2);

        // First column contains labels
        paymentCalc.setCellAt("\"Loan Amount\"", 0, 0);
        paymentCalc.setCellAt("\"Interest\"",    1, 0);
        paymentCalc.setCellAt("\"#Payments\"",   2, 0);
        paymentCalc.setCellAt("\"Payment\"",     3, 0);

        // Second column contains starting values and the formula

        // Normally, I'd never dream of using doubles for dollar amounts, but in
        // this example we need a power function, and the only one we can get
        // easily is defined in the java.lang.Math class as taking double args.
        // This could be worked around by selectively casting/converting the
        // appropriate values to doubles, but it makes the functor a lot
        // more complicated.  (That's supposed to be reassuring -- as if what
        // follows isn't complicated enough!)
        
//         paymentCalc.setCellAt(Double.class,new Double(1000.0D),0, 1, true);
//         paymentCalc.setCellAt(Double.class,new Double(0.05D),  1, 1, true);
//         paymentCalc.setCellAt(Double.class,new Double(24.0D),  2, 1, true);
        paymentCalc.setCellAt("1000.0D",0, 1, true);
        paymentCalc.setCellAt("0.05D",  1, 1, true);
        paymentCalc.setCellAt("24.0D",  2, 1, true);

        // Generators that pull the first three values.  For a complicated
        // formula, this makes assembly easier

        Generator<Double> loanAmt = paymentCalc.getCellAt(0,1).getReference();
        Generator<Double> intRate = paymentCalc.getCellAt(1,1).getReference();
        Generator<Double> noPymts = paymentCalc.getCellAt(2,1).getReference();

        // The Payment Formula is
        //     pymtAmt = intRate * loanAmt / ((1 - (1 + intRate)) ** (numPymts * -1))

        // First off, there're a couple of instances of 1 in the formula
        Constant<Double> one = new Constant<Double>(new Double(1D));

        // The numerator is fairly easy: multiply the interest rate by the loan amount
        Generator<Double> numerator =
            new Multiplies<Double>(Double.class).generate(intRate, loanAmt);

        // In the denominator, we'll need to negate the number of payments ...
        Generator<Double> negPymts = new Negate<Double>(Double.class).generate(noPymts);

        // .. and we'll need (1 + intRate)
        Generator<Double> intRatePlusOne = new Plus<Double>(Double.class).generate(one, intRate);

        // There isn't a power functor in the arithmetic package.  There is one in the
        // java.lang.Math class that takes doubles.
        BinaryFunctor<Math,Object[],Double> powerMethod =
            new InvokeMethod<Math,Double>(Math.class,"pow", new Class[]{Double.TYPE,Double.TYPE});

        // Bind the first argument to null, since power is a static method.  The first argument
        // to InvokeMethod is the object on which the method is called.
        UnaryFunctor<Object[],Double> power = powerMethod.bind1st(null);            

        // To pass the InterestRate and #Payments to the power fn, they'll need to be in an array
        Generator<Object[]> pymtArgs =
            new ArrayBinary<Double,Double>().generate(intRatePlusOne, negPymts);
     
        // bind the power functor to its arguments
        Generator<Double> intRatePowNumPymts = power.generate(pymtArgs);
     
        // Now, the denomonator is a simple subtraction ...
        Generator<Double> denominator =
            new Minus<Double>(Double.class).generate(one, intRatePowNumPymts);
     
        // ... and the formula is a simple division
        Generator<Double> pymtFormula =
            new Divides<Double>(Double.class).generate(numerator, denominator);


//        //---------------------------------------------------------------------------
//        // Here's the same thing in a much more concise form.  It was taken from the
//        // code above, simply substuting each temporary value with its definition,
//        // in turn, and coming up with a somewhat readable format
        
//         Constant<Double> one = new Constant<Double>(new Double(1D));

//         Class[] powTypes = new Class[]{Double.TYPE,Double.TYPE};
//         UnaryFunctor<Object[],Double> power =
//             new InvokeMethod<Math,Double>(Math.class,"pow", powTypes).bind1st(null);

//         Generator<Double> pymtFormula =
//             new Divides<Double>(Double.class).generate(
//                 new Multiplies<Double>(Double.class).generate(intRate, loanAmt),
//                 new Minus<Double>(Double.class).generate(one, power.generate(
//                      new ArrayBinary<Double,Double>().generate(
//                          new Plus<Double>(Double.class).generate(one, intRate),
//                          new Negate<Double>(Double.class).generate(noPymts)))));
//        //---------------------------------------------------------------------------
        
        // Put it in the table
        paymentCalc.setCellAt(Double.class, pymtFormula, 3, 1);

        // One last thing: set the formats of each of the number cells
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        UnaryFunctor<Double,String> currencyFormat = new FormatValue<Double>(currency);
//         UnaryFunctor<String,Double> currencyParser =
//             new ParseFormat.Number<Double>(Double.class, currency);

        NumberFormat percent = NumberFormat.getPercentInstance();
        UnaryFunctor<Double,String> percentFormat = new FormatValue<Double>(percent);
//         UnaryFunctor<String,Double> percentParser =
//             new ParseFormat.Number<Double>(Double.class, percent);

        NumberFormat number = NumberFormat.getNumberInstance();
        UnaryFunctor<Double,String> numberFormat = new FormatValue<Double>(number);
//         UnaryFunctor<String,Double> numberParser =
//             new ParseFormat.Number<Double>(Double.class, number);

        paymentCalc.getCellAt(0,1).setFormat(currencyFormat/*, currencyParser*/);
        paymentCalc.getCellAt(1,1).setFormat(percentFormat/*, percentParser*/);
        paymentCalc.getCellAt(2,1).setFormat(numberFormat/*, numberParser*/);
        paymentCalc.getCellAt(3,1).setFormat(currencyFormat);

        // Pack the whole thing in a Frame and show it
        JFrame frame = new JFrame("Payment Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(paymentCalc);
        frame.pack();
        frame.show();
    }
}
