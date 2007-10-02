// ============================================================================
// $Id: GenericTableDemo.java,v 1.2 2006/12/16 16:48:57 davidahall Exp $
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.sf.jga.swing.GenericTableModel;

public class GenericTableDemo {
    static public class Item {
        private Integer id;
        private String name;
        private String desc;
        private Integer count;
        private BigDecimal price;
    
        public Item(Integer id, String name, String desc, Integer count, BigDecimal price) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.count = count;
            this.price = price;
        }

        public Item(Integer id, String name) {
            this(id, name, "", 0, BigDecimal.ZERO);
        }
    
        public Integer getID() { return id; }
        public String getName() { return name; }
        public void setName(String newName) { name = newName; }
        public String getDesc() { return desc; }
        public void setDesc(String newDesc) { desc = newDesc; }
        public Integer getCount() { return count; }
        public void setCount(Integer newCount) { count = newCount; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal newPrice) { price = newPrice; }
    }

    static public void main (String[] args) {
        List<Item> data = Arrays.asList(new Item[] {
            new Item(0,"Widget","Simple Widget",21,new BigDecimal("15.99")),
            new Item(1,"Gizmo","Complex Widget",57,new BigDecimal("24.99")),
            new Item(2,"Whatzit","Premium Widget",11,new BigDecimal("32.99")),
            new Item(3,"Thingamajig","Overpriced Widget",94,new BigDecimal("74.99")),
            new Item(4,"Foobar"),
            new Item(5,"Doodad","Not a Widget",338,new BigDecimal("9.99"))
        });
        
        GenericTableModel<Item> model =
            new GenericTableModel<Item>(Item.class,data);

        // read-only columns
        model.addColumn(Integer.class, "ID");
        model.addColumn(String.class, "Name");
        
        // editable columns
        model.addColumn(String.class, "Desc", true);
        model.addColumn(Integer.class, "Count", true);
        model.addColumn(BigDecimal.class, "Price", true);
 
        JTable table = 
            new JTable(model, model.getColumnModel());

        JScrollPane scroll = new JScrollPane(table);

        JFrame frame = new JFrame("Generic Table Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scroll);
        frame.pack();
        frame.setVisible(true);
    }
}
