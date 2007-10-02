// ============================================================================
// $Id: Fruit.java,v 1.7 2006/12/16 16:48:57 davidahall Exp $
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
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import net.sf.jga.fn.BinaryFunctor;
import net.sf.jga.fn.Generator;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.adaptor.GenerateBinary;
import net.sf.jga.fn.adaptor.Random;
import net.sf.jga.fn.logical.UnaryNegate;
import net.sf.jga.util.CachingIterator;
import net.sf.jga.util.FindIterator;
import net.sf.jga.util.GenericComparator;
import net.sf.jga.util.LookAheadIterator;

import static net.sf.jga.fn.comparison.ComparisonFunctors.*;
import static net.sf.jga.fn.logical.LogicalFunctors.*;
import static net.sf.jga.fn.property.PropertyFunctors.*;
import static net.sf.jga.algorithms.Filter.*;
import static net.sf.jga.algorithms.Merge.*;
import static net.sf.jga.algorithms.Sort.*;
import static net.sf.jga.algorithms.Transform.*;
import static net.sf.jga.algorithms.Unique.*;

public class Fruit {

    public Fruit(String name, PlantType type) {
        this.name = name;
        this.type = type;
    }
    
    // - - - - - - - - -
    // Name property
    // - - - - - - - - -
    private String name;

    /**
     * Get the Name value.
     * @return the Name value.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the Name value.
     * @param newName The new Name value.
     */
    public void setName(String newName) {
        name = newName;
    }

    // - - - - - - - - -
    // Type property
    // - - - - - - - - -
    public enum PlantType { tree, vine, bush };

    private PlantType type;

    /**
     * Get the Type value.
     * @return the Type value.
     */
    public PlantType getType() {
        return type;
    }

    /**
     * Set the Type value.
     * @param newType The new Type value.
     */
    public void setType(PlantType newType) {
        type = newType;
    }

    public String toString() { return getName(); }


    // - - - - - - - - -
    // Fruit Comparator
    // - - - - - - - - -

    static public Comparator<Fruit> comp =
    new GenericComparator<Fruit,String>(getProperty(Fruit.class, "Name", String.class)); 

    // A few example fruit objects
    
    static public final Fruit APPLE = new Fruit("apple",           PlantType.tree);
    static public final Fruit BANANA = new Fruit("banana",         PlantType.tree);
    static public final Fruit BLACKBERRY = new Fruit("blackberry", PlantType.bush);
    static public final Fruit BLUEBERRY = new Fruit("blueberry",   PlantType.bush);
    static public final Fruit CHERRY = new Fruit("cherry",         PlantType.tree);
    static public final Fruit GRAPE = new Fruit("grape",           PlantType.vine);
    static public final Fruit GRAPEFRUIT = new Fruit("grapefruit", PlantType.tree);
    static public final Fruit LEMON = new Fruit("lemon",           PlantType.tree);
    static public final Fruit LIME = new Fruit("lime",             PlantType.tree);
    static public final Fruit MANGO = new Fruit("mango",           PlantType.tree);
    static public final Fruit ORANGE = new Fruit("orange",         PlantType.tree);
    static public final Fruit PEAR = new Fruit("pear",             PlantType.tree);
    static public final Fruit STRAWBERRY = new Fruit("strawberry", PlantType.bush);
    static public final Fruit TOMATO = new Fruit("tomato",         PlantType.bush);
    static public final Fruit WATERMELON = new Fruit("watermelon", PlantType.vine);

    // a few equivalent items
    
    static public final Fruit GRANNY_SMITH = new Fruit("apple",    PlantType.tree);
    static public final Fruit BARTLETT = new Fruit("pear",         PlantType.tree);
    static public final Fruit ANJOU = new Fruit("pear",            PlantType.tree);
    static public final Fruit VALENCIA = new Fruit("orange",       PlantType.tree);
    static public final Fruit KEYLIME = new Fruit("lime",          PlantType.tree);
    
    // the main alphabetic list of fruts
    static public final List<Fruit> fruits = Arrays.asList(
         APPLE, BANANA, BLACKBERRY, BLUEBERRY, CHERRY, GRAPE, GRAPEFRUIT,
         LEMON, LIME, MANGO, ORANGE, PEAR, STRAWBERRY, TOMATO, WATERMELON);

    // the same list of fruits, ordered by type of fruit
    static public final List<Fruit> fruitsByType = Arrays.asList(
         APPLE, BANANA, CHERRY, GRAPEFRUIT, LEMON, LIME, MANGO, ORANGE,
         PEAR, BLACKBERRY, BLUEBERRY, STRAWBERRY, TOMATO, GRAPE, WATERMELON);

    // a smaller list with duplicate entries
    static public final List<Fruit> citrus = Arrays.asList(
         GRAPEFRUIT, LEMON, LEMON, LEMON, LIME, LIME, ORANGE, ORANGE );

    // another list with equivalent entries
    static public final List<Fruit> dupfruits = Arrays.asList(
         APPLE, GRANNY_SMITH, ORANGE, VALENCIA, PEAR, ANJOU, BARTLETT, LIME, KEYLIME);

    // a short list of yellow fruis
    static public final List<Fruit> yellow = Arrays.asList(BANANA, LEMON, PEAR);

    
    static public void main(String[] args) {
        // ==============================
        // Use a functor as a list filter
        // ==============================
     
        UnaryFunctor<Fruit,Boolean> growsOnTree = compareProperty(Fruit.class,"Type",PlantType.tree);

        System.out.println();
        System.out.println("Grows on Trees");
        System.out.println("==============");
        for(Fruit f : filter(fruits, growsOnTree)) {
            System.out.println(f);
        }

        // The same idea, just with a different filter
     
        UnaryFunctor<Fruit,Boolean> noTreeFruits = unaryNegate(growsOnTree);
     
        System.out.println();
        System.out.println("Don't grow on Trees");
        System.out.println("===================");
        for(Fruit f : filter(fruits, noTreeFruits)) {
            System.out.println(f);
        }
        
        // ==============================
        // Remove adjacent duplicates
        // ==============================
     
        System.out.println();
        System.out.println("Unique Citrus");
        System.out.println("=============");
        for(Fruit f : unique(citrus)) {
            System.out.println(f);
        }

        // another form of unique() takes a functor to compare
        // adjacent entries.  
        BinaryFunctor<Fruit,Fruit,Boolean> sameKind = equalTo(Fruit.comp);
  
        System.out.println();
        System.out.println("Unique Kinds");
        System.out.println("============");
        for(Fruit f : unique(dupfruits, sameKind)) {
            System.out.println(f);
        }

        // ==============================
        // Transform entries in a list
        // ==============================
     
        UnaryFunctor<Fruit,PlantType> getType = getProperty(Fruit.class,"Type", PlantType.class);

        System.out.println();
        System.out.println("Fruit Types");
        System.out.println("============");
        for(PlantType f : transform(fruitsByType, getType)) {
            System.out.println(f);
        }

        // remove the duplicate types
        System.out.println();
        System.out.println("Unique Types");
        System.out.println("============");
        for(PlantType f : unique(transform(fruitsByType, getType))) {
            System.out.println(f);
        }
        
        // ==============================
        // Sort entries in a list
        // ==============================
     
        System.out.println();
        System.out.println("Sorted Fruit");
        System.out.println("============");
        for(Fruit f : sort(fruitsByType, comp)) {
            System.out.println(f);
        }

        // make a list of unique types
        List<PlantType> types = new ArrayList<PlantType>();
        unique(sort(transform(fruits, getType)), types);
        System.out.println();
        System.out.println("Fruit Types");
        System.out.println("============");
        for(PlantType f : types) {
            System.out.println(f);
        }
        

        // ==============================
        // Merge multiple lists
        // ==============================
     
        System.out.println();
        System.out.println("Merged Yellow & Citrus Fruits");
        System.out.println("=============================");
        for(Fruit f : merge(yellow, citrus, Fruit.comp)) {
            System.out.println(f);
        }

        // remove duplicates in the merged list
        System.out.println();
        System.out.println("Merged Yellow & Citrus, no dupes");
        System.out.println("================================");
        for(Fruit f : unique(merge(yellow, citrus, Fruit.comp))) {
            System.out.println(f);
        }

        // append the lists, rather than merge them
        System.out.println();
        System.out.println("Appended Yellow & Citrus Fruits");
        System.out.println("===============================");
        for(Fruit f : append(yellow, unique(citrus)))
        {
            System.out.println(f);
        }

        // shuffle two lists, rather than merge them.  first, we need a generator
        // that simulates fliping a coin...

        Generator<Boolean> coinflip = lessEqual(Double.class).bind2nd(0.5d).generate(new Random());

        // ... and we bind that into a GenerateBinary, which will accept and ignore
        // two fruit arguements
     
        BinaryFunctor<Fruit,Fruit,Boolean> shuffle = 
            new GenerateBinary<Fruit,Fruit,Boolean>(coinflip);
     
        System.out.println();
        System.out.println("Shuffled Yellow & Citrus Fruits");
        System.out.println("===============================");
        for(Fruit f : merge(yellow, unique(citrus), shuffle)) {
            System.out.println(f);
        }

//         // =================================
//         // Look Ahead of the current postion
//         // =================================
        
//         System.out.println();
//         System.out.println("Looking Ahead: Yellow fruits");
//         System.out.println("============================");
//         LookAheadIterator<Fruit> lai =
//             new LookAheadIterator<Fruit>(yellow.iterator());
//         for(Fruit f : lai) {
//             Fruit f1 = lai.hasNextPlus(1) ? lai.peek(1) : null;
//             System.out.println(f +"\t"+f1);
//         }
                
//         // =================================
//         // Look behind the current postion
//         // =================================
        
//         System.out.println();
//         System.out.println("Looking Behind: Yellow fruits");
//         System.out.println("=============================");
//         CachingIterator<Fruit> ci =
//             new CachingIterator<Fruit>(yellow.iterator(), 2);
//         for(Fruit f : ci) {
//             Fruit f1 = ci.hasCached(2) ? ci.cached(2) : null;
//             System.out.println(f +"\t"+f1);
//         }

//         // =================================
//         // selectively skip entries
//         // =================================

//         // skips all of the trees at the beginning of the alphabetic list
        
//         System.out.println();
//         System.out.println("Skipping initial Trees");
//         System.out.println("======================");
//         FindIterator<Fruit> fi1 = new FindIterator<Fruit>(fruits.iterator());
//         fi1.findNext(noTreeFruits);
//         for(Fruit f : fi1) {
//             System.out.println(f);
//         }

//         // skips all but the first tree in any
//         // sequence of trees
        
//         System.out.println();
//         System.out.println("Finding first non-tree after every tree");
//         System.out.println("=======================================");
//         FindIterator<Fruit> fi2 = new FindIterator<Fruit>(fruits.iterator());
//         for(Fruit f : fi2) {
//             if (growsOnTree.fn(f))
//                 fi2.findNext(noTreeFruits);
            
//             System.out.println(f);
//         }
        
//         System.out.println();
    }
}
