package om.frankc.csc435.compiler.util;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class EnvironmentTest {

    final Environment<String, Object> mEnvironment = new Environment<>();

    @Test
    public void testDefaultState() {
        assertFalse(mEnvironment.containsKey("one"));

        final Optional<Object> result = mEnvironment.get("one");
        assertFalse(result.isPresent());
    }

    @Test
    public void testAddRetrieveKey() {
        final Object one = new Object();
        final Object two = new Object();

        mEnvironment.put("one", one);
        mEnvironment.put("two", two);

        final Optional<Object> resultOne = mEnvironment.get("one");
        assertTrue(resultOne.isPresent());
        assertEquals(one, resultOne.get());

        final Optional<Object> resultTwo = mEnvironment.get("two");
        assertTrue(resultTwo.isPresent());
        assertEquals(two, resultTwo.get());
    }

    @Test
    public void testScopes() {
        final Object oneOuter = new Object();
        final Object oneInner = new Object();
        final Object two = new Object();

        mEnvironment.put("one", oneOuter);
        mEnvironment.put("two", two);

        mEnvironment.beginScope();
        mEnvironment.put("one", oneInner);

        final Optional<Object> resultOneInner = mEnvironment.get("one");
        assertTrue(resultOneInner.isPresent());
        assertEquals(oneInner, resultOneInner.get());

        final Optional<Object> resultTwo = mEnvironment.get("two");
        assertTrue(resultTwo.isPresent());
        assertEquals(two, resultTwo.get());

        mEnvironment.endScope();

        final Optional<Object> resultOneOuter = mEnvironment.get("one");
        assertTrue(resultOneOuter.isPresent());
        assertEquals(oneOuter, resultOneOuter.get());
    }

}