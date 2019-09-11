import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Nam B Nguyen and Simon Manning
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /*
     * Test case for add
     */

    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        m.add("red");
        assertEquals(mExpected, m);
    }

    /*
     * Test case for change to extraction mode
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue");
        m.changeToExtractionMode();
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /*
     * Test case for RemoveFirst
     */

    @Test
    public final void testRemoveFirstToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals("green", m.removeFirst());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "blue");
        assertEquals(mExpected.removeFirst(), m.removeFirst());
        assertEquals(mExpected, m);
    }

    /*
     * Test case for isInInsertionMode
     */
    @Test
    public final void testIsInInsertionModeFalseEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeFalseNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrueEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrueNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /*
     * Test Order
     */

    @Test
    public final void testOrderInsertionNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3");
        assertEquals(m.order(), ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderExtractionNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3");
        assertEquals(m.order(), ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderInsertionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(m.order(), ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderExtractionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(m.order(), ORDER);
        assertEquals(mExpected, m);
    }

    /*
     * Test case for size
     */

    @Test
    public final void testSizeEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(0, m.size());
        assertEquals(mExpected, m);

    }

    @Test
    public final void testSizeNonEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red",
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "green");
        assertEquals(2, m.size());
        assertEquals(mExpected, m);

    }

    @Test
    public final void testSizeEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonEmptInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "green");
        assertEquals(2, m.size());
        assertEquals(mExpected, m);
    }

}
