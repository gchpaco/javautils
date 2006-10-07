package util;

import static org.testng.AssertJUnit.*;

import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SingletonTest {
    @BeforeMethod
    public void clearSingleton() {
        SingletonManager.clear();
    }

    @Test
    public void testSimpleRegistration() {
        Pair<Integer, Integer> pair = Pair.make(1, 2);
        assertSame(pair, SingletonManager.register(Pair.class, pair));
        assertSame(pair, SingletonManager.get(Pair.class));
    }

    @Test
    public void registrationSame() {
        HashMap first = new HashMap ();
        HashMap second = new HashMap ();
        assertSame(SingletonManager.register(HashMap.class, first), SingletonManager
                .register(HashMap.class, second));
    }

    @Test
    public void constructionFailure() {
        try {
            SingletonManager.get(Pair.class);
            fail("Shouldn't be able to create a pair with no information!");
        } catch (IllegalArgumentException e) {
            assertEquals("No instance of util.Pair registered", e.getMessage());
        }
    }
}
