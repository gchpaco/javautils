package util.purefun;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class SplayHeapTest {
    public void empty() {
	Heap<Integer> heap = SplayHeap.empty ();
	assertTrue(heap.isEmpty());
	try {
	    heap.findMin();
	    fail("shouldn't succeed at findMin on an empty heap!");
	} catch (EmptyException e) {
	}
	try {
	    heap.deleteMin();
	    fail("shouldn't succeed at findMin on an empty heap!");
	} catch (EmptyException e) {
	}
    }
    public void simplePersistence() throws EmptyException {
	Heap<Integer> heap, heap2, heap3;
	heap = SplayHeap.empty ();
	heap2 = heap.insert(5);
	assertFalse(heap2.isEmpty());
	assertTrue(heap.isEmpty());
	assertEquals((Integer)5,heap2.findMin());
	heap3 = heap2.deleteMin();
	assertTrue(heap3.isEmpty());
	assertFalse(heap2.isEmpty());
	assertTrue(heap.isEmpty());
    }
    public void insertionInOrder() {
	int [] testData = { 6, 4, 2, 7, 8, 1, 3, 0, 6, 22 };
	Heap<Integer> heap = SplayHeap.empty ();
	for (int i : testData)
	    heap = heap.insert(i);
	int [] inorderData = { 0, 1, 2, 3, 4, 6, 6, 7, 8, 22 };
	try {
	    for (int i : inorderData) {
		assertEquals((Integer)i, heap.findMin());
		heap = heap.deleteMin();
	    }
	} catch (EmptyException e) {
	    fail("None of this should ever trigger an empty exception.");
	}
    }
    public void merging() throws EmptyException {
	Heap<Integer> heap, heap2, heap3;
	int [] testData = { 6, 4, 2, 7, 8 };
	heap = SplayHeap.empty ();
	for (int i : testData)
	    heap = heap.insert(i);
	int [] testData2 = { 1, 3, 0, 6, 22 };
	heap2 = SplayHeap.empty ();
	for (int i : testData2)
	    heap2 = heap2.insert(i);
	int [] inorderData = { 0, 1, 2, 3, 4, 6, 6, 7, 8, 22 };
	heap3 = heap2.merge(heap);
	try {
	    for (int i : inorderData) {
		assertEquals((Integer)i, heap3.findMin());
		heap3 = heap3.deleteMin();
	    }
	} catch (EmptyException e) {
	    fail("None of this should ever trigger an empty exception.");
	}
	heap3 = heap.merge(heap2);
	try {
	    for (int i : inorderData) {
		assertEquals((Integer)i, heap3.findMin());
		heap3 = heap3.deleteMin();
	    }
	} catch (EmptyException e) {
	    fail("None of this should ever trigger an empty exception.");
	}
    }
}