// ============================================================================
// $Id: FormSheet.java,v 1.3 2005/08/31 13:59:18 davidahall Exp $
// Copyright (c) 2005  David A. Hall
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
 * FormSheet.java
 * <p>
 * Copyright &copy; 2005  David A. Hall
 */

import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import net.sf.jga.fn.Generator;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.parser.FunctorParser;
import net.sf.jga.parser.GenericParser;
import net.sf.jga.parser.ParseException;
import net.sf.jga.swing.FunctorProxy;
import net.sf.jga.swing.spreadsheet.Cell;
import net.sf.jga.swing.spreadsheet.ComponentEditor;
import net.sf.jga.swing.spreadsheet.ComponentRenderer;
import net.sf.jga.swing.spreadsheet.Spreadsheet;

public class FormSheet {
    static public void main(String[] args) {
        Spreadsheet sheet = new Spreadsheet(5,3);
        sheet.setShowGrid(false);
        sheet.setEditableByDefault(true);

        JButton button = new JButton("Click Me");
        Cell cell = sheet.setCellAt(JButton.class, button, 1, 1);
        cell.setEditable(true);
        cell.setRenderer(ComponentRenderer.getInstance());
        cell.setEditor(ComponentEditor.getInstance());

        sheet.setCellAt("cell(1,1); new java.util.Date()", 3, 1);

        try {
            String tickleExp = "this.getCellAt(1,1).notifyObservers()";
            Proxy proxy = FunctorProxy.makeListenerFor(button);
            FunctorProxy.register(proxy, "actionPerformed",
                                  sheet.getParser().parseUnary(tickleExp, Object.class));
         
            button.addActionListener((ActionListener) proxy);
        }
        catch (NoSuchMethodException x) { x.printStackTrace(); System.exit(1); }
        catch (ParseException x) { x.printStackTrace(); System.exit(1); }
        
        JFrame frame = new JFrame("FormSheet Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(sheet);
        frame.pack();
        frame.setVisible(true);
    }
}
