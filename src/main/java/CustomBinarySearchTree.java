public class CustomBinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public CustomBinarySearchTree() {
        this.root = null;
    }

    public CustomBinarySearchTree(T element) {
        this.root = new Node<T>(element, null, null, null);
    }

    public void add(T element) {

        Node<T> newNode = new Node<T>(element, null, null, null);

        /*
         * If tree is empty, set new element as root and you are done.
         */
        if (this.getRoot() == null) {
            this.setRoot(newNode);
            return;
        }

        Node<T> currentNode = this.getRoot();
        Node<T> parentNode = null;

        while (currentNode != null) {
            parentNode = currentNode;
            if (element.compareTo(currentNode.getData()) <= 0) {
                currentNode = currentNode.getLeftNode();
            } else {
                currentNode = currentNode.getRightNode();
            }
        }

        currentNode = newNode;

        if (parentNode == null) {
            throw new NullPointerException("Somehow parent of newly-inserted node is null. This should not happen, meaning the logic is broken.");
        }

        try {
            if (currentNode.getData().compareTo(parentNode.getData()) <= 0) {
                parentNode.setLeftNode(currentNode);
            } else {
                parentNode.setRightNode(currentNode);
            }
            currentNode.setParent(parentNode);
        } catch(NullPointerException e) {
            throw new NullPointerException("There is a node with null data in the tree.");
        }
    }

    public boolean contains(T element) {
        Node<T> currentNode = this.getRoot();

        while (currentNode != null && !element.equals(currentNode.getData())) {
            if (element.compareTo(currentNode.getData()) <= 0) {
                currentNode = currentNode.getLeftNode();
            } else {
                currentNode = currentNode.getRightNode();
            }
        }

        return currentNode != null;
    }

    public boolean remove(T element) {
        Node<T> targetNode = this.find(element);

        if (targetNode == null) {
            return false;
        }

        if (!targetNode.hasChildren()) {
            if (targetNode == this.getRoot()) {
                this.setRoot(null);
            } else {
                Node<T> parentNode = targetNode.getParent();

                if (targetNode == parentNode.getLeftNode()) {
                    parentNode.setLeftNode(null);
                } else {
                    parentNode.setRightNode(null);
                }
            }
        } else if (targetNode.hasExactlyOneChild()) {
            if (targetNode == this.getRoot()) {
                /*
                 * Check which child it is and update pointers.
                 */
                if (targetNode.getLeftNode() != null) {
                    this.setRoot(targetNode.getLeftNode());
                    targetNode.getLeftNode().setParent(null);
                } else {
                    this.setRoot(targetNode.getRightNode());
                    targetNode.getRightNode().setParent(null);
                }
            } else {
                Node<T> parentNode = targetNode.getParent();
                Node<T> childNode = null;

                /*
                 * Check which (left/right) child the CHILD node is and update childNode.
                 */
                if (targetNode.getLeftNode() != null) {
                    childNode = targetNode.getLeftNode();
                } else {
                    childNode = targetNode.getRightNode();
                }

                /*
                 * Check which (left/right) child the TARGET node is and update pointers.
                 */
                if (parentNode.getLeftNode() == targetNode) {
                    parentNode.setLeftNode(childNode);
                } else {
                    parentNode.setRightNode(childNode);
                }

                childNode.setParent(parentNode);
            }
        }

        if (targetNode.hasExactlyTwoChildren()) {
            /*
             * First splice out the successor of the target node.
             */
            Node<T> successorNode = this.findSuccessorOfNode(targetNode);
            this.remove(successorNode.getData());

            /*
             * Then replace the target node with its successor.
             */
            if (targetNode == this.getRoot()) {
                this.setRoot(successorNode);
            } else {
                Node<T> parentNode = targetNode.getParent();

                if (targetNode == parentNode.getLeftNode()) {
                    parentNode.setLeftNode(successorNode);
                } else {
                    parentNode.setRightNode(successorNode);
                }
            }

            successorNode.setLeftNode(targetNode.getLeftNode());
            successorNode.setRightNode(targetNode.getRightNode());
            successorNode.setParent(targetNode.getParent());
        }

        /*
         * Clean up.
         */
        targetNode.setLeftNode(null);
        targetNode.setRightNode(null);
        targetNode.setParent(null);

        return true;
    }

    public Node<T> find(T element) {
        Node<T> currentNode = this.getRoot();

        while (currentNode != null && !currentNode.getData().equals(element)) {
            if (element.compareTo(currentNode.getData()) < 0) {
                currentNode = currentNode.getLeftNode();
            } else {
                currentNode = currentNode.getRightNode();
            }
        }

        return currentNode;
    }

    public Node<T> findMinimumRelativeToNode(Node<T> startNode) {
        Node<T> currentNode = startNode;

        if (currentNode == null) {
            throw new NullPointerException("Cannot find minimum of a tree starting with a null node.");
        }

        while(currentNode.getLeftNode() != null) {
            currentNode = currentNode.getLeftNode();
        }

        return currentNode;
    }

    public Node<T> findMaximumRelativeToNode(Node<T> startNode) {
        Node<T> currentNode = startNode;

        if (currentNode == null) {
            throw new NullPointerException("Cannot find maximum of a tree starting with a null node.");
        }

        while(currentNode.getRightNode() != null) {
            currentNode = currentNode.getRightNode();
        }

        return currentNode;
    }

    public Node<T> findSuccessorOfNode(Node<T> startNode) {

        if (startNode == null) {
            throw new NullPointerException("You cannot search for a successor of a null node.");
        }

        if (startNode.getRightNode() != null) {
            return findMinimumRelativeToNode(startNode.getRightNode());
        } else {
            Node<T> currentNode = startNode;
            Node<T> parentNode = currentNode.getParent();

            while (parentNode != null && currentNode == parentNode.getRightNode()) {
                parentNode = parentNode.getParent();
                currentNode = currentNode.getParent();
            }

            return parentNode;
        }
    }

    public Node<T> findPredecessorOfNode(Node<T> startNode) {

        if (startNode == null) {
            throw new NullPointerException("You cannot search for a predecessor of a null node.");
        }

        if (startNode.getLeftNode() != null) {
            return findMaximumRelativeToNode(startNode.getLeftNode());
        } else {
            Node<T> currentNode = startNode;
            Node<T> parentNode = currentNode.getParent();

            while (parentNode != null && currentNode == parentNode.getLeftNode()) {
                parentNode = parentNode.getParent();
                currentNode = currentNode.getParent();
            }

            return parentNode;
        }
    }

    protected static class Node<T> {
        private T data;
        private Node<T> leftNode;
        private Node<T> rightNode;

        private Node<T> parent;

        public Node() {
            this(null, null, null, null);
        }

        public Node(T data) {
            this(data, null, null, null);
        }

        public Node(T data, Node<T> leftNode, Node<T> rightNode, Node<T> parent) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.parent = parent;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node<T> leftNode) {
            this.leftNode = leftNode;
        }

        public Node<T> getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node<T> rightNode) {
            this.rightNode = rightNode;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public boolean hasChildren() {
            return this.getLeftNode() != null || this.getRightNode() != null;
        }

        public boolean hasExactlyOneChild() {
            return this.getLeftNode() != null ^ this.getRightNode() != null;
        }

        public boolean hasExactlyTwoChildren() {
            return this.getLeftNode() != null && this.getRightNode() != null;
        }
    }

}
