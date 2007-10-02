// ============================================================================
// $Id: TimestampSheet.java,v 1.1 2005/12/14 13:39:49 davidahall Exp $
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
 * TimestampSheet.java
 * <p>
 * Copyright &copy; 2005  David A. Hall
 */

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.string.FormatValue;
import net.sf.jga.parser.IParser;
import net.sf.jga.parser.ParseException;
import net.sf.jga.swing.FunctorProxy;
import net.sf.jga.swing.spreadsheet.Cell;
import net.sf.jga.swing.spreadsheet.ComponentEditor;
import net.sf.jga.swing.spreadsheet.ComponentRenderer;
import net.sf.jga.swing.spreadsheet.Spreadsheet;

public class TimestampSheet {
    public TimestampSheet (){}

    static public void main(String[] args) {
        final Spreadsheet sheet = new Spreadsheet(5,3);

        // Turns off the grid, both during the paint method and when interacting
        // with individual cells (if a cell in a spreadsheet is not editable,
        // then there is little reason to select it, so the default renderer for
        // cells does not paint any selection/focus graphics)
        sheet.setShowGrid(false);
        sheet.setEditableByDefault(false);

        // This cell contains a button.  We'll use the new ComponentEditor and
        // ComponentRenderer classes to show display and interact with the
        // button at runtime.  Note that for the Editor to come into play, the
        // cell must be editable.
        JButton button = new JButton("Click Me");
        Cell buttonCell = sheet.setCellAt(JButton.class, button, 1, 1);
        buttonCell.setEditable(true);
        buttonCell.setRenderer(ComponentRenderer.getInstance());
        buttonCell.setEditor(ComponentEditor.getInstance());

        // This cell contains a timestamp that is generated in response to
        // button clicks.  The compound formula doesn't use the contents of
        // the button cell for anything other than to establish a relationship
        // between this cell and the button cell.
        DateFormat format = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        Cell timeCell = sheet.setCellAt("cell(1,1); new java.util.Date()", 3, 1);
        timeCell.setFormat(new FormatValue(format));
        
        // We use the sheet's parser to parse expressions because it
        // recognizes the spreadsheet extensions to the expression
        // grammar, and because the sheet is already bound to the
        // 'this' keyword.
        IParser parser = sheet.getParser();

        try {
            Proxy proxy = FunctorProxy.makeListenerFor(button);

            // This expression will call the button cell's notifyObservers()
            // method.  Here, we're using FunctorProxy to wire this expression
            // into the button's actionPerformed method
            String tickleExp = "this.getCellAt(1,1).notifyObservers()";
            UnaryFunctor tickle = parser.parseUnary(tickleExp, Object.class);
            FunctorProxy.register(proxy, "actionPerformed", tickle);
            button.addActionListener((ActionListener) proxy);

            // We could repeat the last cluster as often as necessary to listen
            // to as many types of events a desired.
        }
        catch (NoSuchMethodException x) { x.printStackTrace(); System.exit(1); }
        catch (ParseException x) { x.printStackTrace(); System.exit(1); }

        // For now, we have to adjust the column widths and row heights manually.
        // I expect the Spreadsheet class (or some associated class) to grow the
        // capability to adjust these automatically
        Dimension buttonSize = button.getPreferredSize();
        Dimension cellSpace  = sheet.getIntercellSpacing();
        sheet.setRowHeight(1, buttonSize.height + 2 * cellSpace.height);
        sheet.getColumnModel().getColumn(1).setPreferredWidth(buttonSize.width + 2*cellSpace.width);

        // Reasonably standard startup code.
        JFrame frame = new JFrame("TimestampSheet Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(sheet);
        frame.pack();
        frame.setVisible(true);
    }
}
