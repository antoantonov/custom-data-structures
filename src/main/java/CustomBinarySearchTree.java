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
        this.root = new Node<T>(element, null, null);
    }

    public void add(T element) {

        Node<T> newNode = new Node<T>(element, null, null);

        /*
         * If tree is empty, set new element as root and you are done.
         */
        if (this.root == null) {
            this.root = newNode;
            return;
        }

        Node<T> currentNode = this.root;
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

        if (currentNode.getData().compareTo(parentNode.getData()) <= 0) {
            parentNode.setLeftNode(currentNode);
        } else {
            parentNode.setRightNode(currentNode);
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

    protected class Node<T> {
        private T data;
        private Node<T> leftNode;
        private Node<T> rightNode;

        public Node() {
            this(null, null, null);
        }

        public Node(T data) {
            this(data, null, null);
        }

        public Node(T data, Node<T> leftNode, Node<T> rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
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
    }

}
