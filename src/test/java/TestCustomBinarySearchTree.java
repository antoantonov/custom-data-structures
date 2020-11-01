import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestCustomBinarySearchTree<T> extends CustomBinarySearchTree {

    private TestCustomBinarySearchTree<Integer> testTree;

    @BeforeMethod
    public void beforeMethod() {
        testTree = new TestCustomBinarySearchTree<Integer>();
    }

    @Test
    public void testAddEightThree() {
        testTree.add(8);
        testTree.add(3);

        TestCustomBinarySearchTree.Node root = testTree.getRoot();
        assertTrue(root.getData().equals(8));
        assertNull(root.getRightNode());
        assertTrue(root.getLeftNode().getData().equals(3));
    }

    @Test
    public void testAddEightTwelveTenEight() {
        testTree.add(8);
        testTree.add(12);
        testTree.add(10);
        testTree.add(8);

        TestCustomBinarySearchTree.Node root = testTree.getRoot();
        assertTrue(root.getData().equals(8));
        assertTrue(root.getLeftNode().getData().equals(8));
        assertTrue(root.getRightNode().getData().equals(12));
        assertTrue(root.getRightNode().getLeftNode().getData().equals(10));
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
        assertTrue(testTree.findMinimumRelativeToNode(testTree.getRoot()).getData().equals(2));
        assertTrue(testTree.findMinimumRelativeToNode(testTree.getRoot().getRightNode()).getData().equals(11));
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
