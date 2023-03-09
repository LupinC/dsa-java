package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void balance(BinaryNode<T> node) {

        if (node.hasParent() && node.getParent().hasParent() && node.getParent().getParent().hasBothChildren()) {
            BinaryNode<T> parent = node.getParent();
            BinaryNode<T> grandparent = parent.getParent();
            BinaryNode<T> uncle = node.getUncle();

            if ((!parent.hasBothChildren()) && grandparent.isRightChild(parent) && !uncle.hasBothChildren()) {

                if (parent.isLeftChild(node)) {//parent > node
                    if (uncle.hasLeftChild())//uncle > cousin
                    {
                        rotateRight(parent);
                        rotateLeft(grandparent);
                        rotateRight(grandparent);
                    } else {
                        rotateLeft(uncle);
                        rotateRight(parent);
                        rotateLeft(grandparent);
                        rotateRight(grandparent);
                    }
                } else {
                    if (uncle.hasRightChild())//uncle > cousin
                    {
                        rotateLeft(uncle);
                        rotateLeft(grandparent);
                        rotateRight(grandparent);
                    } else {
                        rotateLeft(grandparent);
                        rotateRight(grandparent);
                    }
                }
            }
        }
    }
}

