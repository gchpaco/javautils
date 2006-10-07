package util.purefun;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class QueueTest {
    public void empty() {
        Queue<Integer> queue = BatchedQueue.empty();
        assertTrue(queue.isEmpty());
        try {
            queue.head();
            fail("Shouldn't be able to take the head of an empty queue!");
        } catch (EmptyException e) {
        }
        try {
            queue.tail();
            fail("Shouldn't be able to take the head of an empty queue!");
        } catch (EmptyException e) {
        }
    }

    public void simple() {
        Queue<Integer> queue = BatchedQueue.empty();
        Queue<Integer> queue2 = queue.snoc(6);
        assertFalse(queue2.isEmpty());
        assertTrue(queue.isEmpty());
        try {
            assertEquals((Integer) 6, queue2.head());
            assertTrue(queue2.tail().isEmpty());
        } catch (EmptyException e) {
            fail("Shouldn't throw exceptions on full queues!");
        }
    }

    public void queueing() {
        Queue<Object> queue = BatchedQueue.empty().snoc(6).snoc(5).snoc(2)
                .snoc(7);
        try {
            assertEquals(6, queue.head());
            assertEquals(5, queue.tail().head());
            assertEquals(2, queue.tail().tail().head());
            assertEquals(7, queue.tail().tail().tail().head());
            assertTrue(queue.tail().tail().tail().tail().isEmpty());
        } catch (EmptyException e) {
            fail("Shouldn't throw exceptions on full queues!");
        }
    }
}