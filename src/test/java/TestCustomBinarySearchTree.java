import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestCustomBinarySearchTree<T extends Comparable<T>> extends CustomBinarySearchTree<T> {

    private TestCustomBinarySearchTree<Integer> testTree;

    @BeforeMethod
    public void beforeMethod() {
        testTree = new TestCustomBinarySearchTree<Integer>();
    }

    @Test
    public void testAddEightThree() {
        testTree.add(8);
        testTree.add(3);

        TestCustomBinarySearchTree.Node<Integer> root = testTree.getRoot();
        assertEquals(root.getData().intValue(), 8);
        assertNull(root.getRightNode());
        assertEquals(root.getLeftNode().getData().intValue(), 3);
    }

    @Test
    public void testAddEightTwelveTenEight() {
        testTree.add(8);
        testTree.add(12);
        testTree.add(10);
        testTree.add(8);

        TestCustomBinarySearchTree.Node<Integer> root = testTree.getRoot();
        assertEquals(root.getData().intValue(), 8);
        assertEquals(root.getLeftNode().getData().intValue(), 8);
        assertEquals(root.getRightNode().getData().intValue(), 12);
        assertEquals(root.getRightNode().getLeftNode().getData().intValue(), 10);
    }

    @Test
    public void testContains() {
        fillTreeWithPseudoRandomData();

        assertTrue(testTree.contains(10));
        assertTrue(testTree.contains(13));
        assertTrue(testTree.contains(11));
        assertTrue(testTree.contains(7));
        assertTrue(testTree.contains(4));
        assertTrue(testTree.contains(6));
        assertTrue(testTree.contains(2));
        assertTrue(testTree.contains(12));

        assertFalse(testTree.contains(-41));
        assertFalse(testTree.contains(0));
        assertFalse(testTree.contains(1));
        assertFalse(testTree.contains(8));
        assertFalse(testTree.contains(9));
        assertFalse(testTree.contains(99));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMinimumEmptyTree() {
        testTree.findMinimumRelativeToNode(null);
    }

    @Test
    public void testMinimumOnSampleTree() {
        fillTreeWithPseudoRandomData();
        assertEquals(testTree.findMinimumRelativeToNode(testTree.getRoot()).getData().intValue(), 2);
        assertEquals(testTree.findMinimumRelativeToNode(testTree.getRoot().getRightNode()).getData().intValue(), 11);
    }

    @Test
    public void testMaximumOnSampleTree() {
        fillTreeWithPseudoRandomData();
        assertEquals(testTree.findMaximumRelativeToNode(testTree.getRoot()).getData().intValue(), 13);
        assertEquals(testTree.findMaximumRelativeToNode(testTree.getRoot().getLeftNode()).getData().intValue(), 7);
    }

    @Test
    public void testSuccessorOnSampleTree() {
        fillTreeWithPseudoRandomData();

        // The successor of the node with value 6 is the node with value 7.
        assertEquals(testTree.findSuccessorOfNode(testTree.getRoot().getLeftNode().getLeftNode().getRightNode()).getData().intValue(), 7);

        // The successor of the node with value 11 is the node with value 12.
        assertEquals(testTree.findSuccessorOfNode(testTree.getRoot().getRightNode().getLeftNode()).getData().intValue(), 12);

        // The node with value 13 has no successor.
        assertNull(testTree.findSuccessorOfNode(testTree.getRoot().getRightNode()));

        // The node with maximal value has no successor.
        assertNull(testTree.findSuccessorOfNode(testTree.findMaximumRelativeToNode(testTree.getRoot())));
    }

    @Test
    public void testPredecessorOnSampleTree() {
        fillTreeWithPseudoRandomData();

        // The predecessor of the node with value 7 is the node with value 2.
        assertEquals(testTree.findPredecessorOfNode(testTree.getRoot().getLeftNode()).getData().intValue(), 6);

        // The predecessor of the node with value 11 is the node with value 10.
        assertEquals(testTree.findPredecessorOfNode(testTree.getRoot().getRightNode().getLeftNode()).getData().intValue(), 10);

        // The node with value 2 has no predecessor.
        assertNull(testTree.findPredecessorOfNode(testTree.getRoot().getLeftNode().getLeftNode().getLeftNode().getLeftNode()));

        // The node with minimal value has no predecessor.
        assertNull(testTree.findPredecessorOfNode(testTree.findMinimumRelativeToNode(testTree.getRoot())));
    }

    @Test
    public void testFindOnSampleTree() {
        fillTreeWithPseudoRandomData();

        assertEquals(testTree.find(10), testTree.getRoot());
        assertEquals(testTree.find(4), testTree.getRoot().getLeftNode().getLeftNode());
        assertEquals(testTree.find(11), testTree.getRoot().getRightNode().getLeftNode());
        assertEquals(testTree.find(6), testTree.getRoot().getLeftNode().getLeftNode().getRightNode());
        assertEquals(testTree.find(1), null);
        assertEquals(testTree.find(99), null);
    }

    @Test
    public void testRemove6FromSampleTree() {
        fillTreeWithPseudoRandomData();

        testTree.remove(6);

        assertEquals(testTree.getRoot().getData().intValue(), 10);
        assertEquals(testTree.getRoot().getLeftNode().getData().intValue(), 7);
        assertEquals(testTree.getRoot().getLeftNode().getLeftNode().getData().intValue(), 4);
        assertNull(testTree.getRoot().getLeftNode().getLeftNode().getRightNode());
        assertNull(testTree.find(6));
    }

    @Test
    public void testRemove13FromSampleTree() {
        fillTreeWithPseudoRandomData();

        testTree.remove(13);

        assertEquals(testTree.getRoot().getData().intValue(), 10);
        assertEquals(testTree.getRoot().getRightNode().getData().intValue(), 11);
        assertEquals(testTree.getRoot().getRightNode().getRightNode().getData().intValue(), 12);
        assertNull(testTree.find(13));
    }

    @Test
    public void testRemove4FromSampleTree() {
        fillTreeWithPseudoRandomData();

        testTree.remove(4);

        assertEquals(testTree.getRoot().getData().intValue(), 10);
        assertEquals(testTree.getRoot().getLeftNode().getData().intValue(), 7);
        assertEquals(testTree.getRoot().getLeftNode().getLeftNode().getData().intValue(), 6);
        assertEquals(testTree.getRoot().getLeftNode().getLeftNode().getLeftNode().getData().intValue(), 4);
        assertNull(testTree.getRoot().getLeftNode().getLeftNode().getRightNode());
        assertEquals(testTree.find(4).getLeftNode().getData().intValue(), 2);
    }

    @Test
    public void testLowerOnSampleTree() {
        fillTreeWithPseudoRandomData();

        assertEquals(testTree.lower(10).getData().intValue(), 7);
        assertEquals(testTree.lower(7).getData().intValue(), 6);
        assertEquals(testTree.lower(4).getData().intValue(), 2);
        assertEquals(testTree.lower(6).getData().intValue(), 4);
        assertNull(testTree.lower(2));
        assertEquals(testTree.lower(13).getData().intValue(), 12);
        assertEquals(testTree.lower(12).getData().intValue(), 11);

        assertEquals(testTree.lower(8).getData().intValue(), 7);
        assertNull(testTree.lower(1));
        assertEquals(testTree.lower(99).getData().intValue(), 13);
    }

    @Test
    public void testHigherOnSampleTree() {
        fillTreeWithPseudoRandomData();

        assertEquals(testTree.higher(10).getData().intValue(), 11);
        assertEquals(testTree.higher(7).getData().intValue(), 10);
        assertEquals(testTree.higher(4).getData().intValue(), 6);
        assertEquals(testTree.higher(6).getData().intValue(), 7);
        assertEquals(testTree.higher(2).getData().intValue(), 4);
        assertNull(testTree.higher(13));
        assertEquals(testTree.higher(12).getData().intValue(), 13);

        assertEquals(testTree.higher(8).getData().intValue(), 10);
        assertNull(testTree.higher(99));
        assertEquals(testTree.higher(1).getData().intValue(), 2);
    }

    /*
     * Fill the tree with the following sample data:
     *
     *         10
     *        /  \
     *       7    13
     *      /    /
     *     4    11
     *    / \     \
     *   4   6     12
     *  /
     * 2
     *
     */
    private void fillTreeWithPseudoRandomData() {
        testTree.add(10);
        testTree.add(13);
        testTree.add(11);
        testTree.add(7);
        testTree.add(4);
        testTree.add(6);
        testTree.add(4);
        testTree.add(2);
        testTree.add(12);
    }

}
