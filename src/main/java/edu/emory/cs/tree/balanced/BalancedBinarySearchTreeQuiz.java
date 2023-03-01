package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.AbstractBinaryNode;
import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void balance(BinaryNode<T> node) {

        BinaryNode parent = node.getParent();
        BinaryNode grandparent = node.getGrandParent();
        BinaryNode uncle = node.getUncle();


        if(node.getParent().getKey().compareTo(node.getKey())>0)
        {
            if(uncle.getKey().compareTo(uncle.getLeftChild().getKey())>0)//uncle > cousin
            {
                rotateRight(parent);
                rotateLeft(grandparent);
                rotateRight(grandparent);
            }

            else
            {
                rotateLeft(uncle);
                rotateRight(parent);
                rotateLeft(grandparent);
                rotateRight(grandparent);
            }
        }
        else
        {
            if(uncle.getKey().compareTo(uncle.getLeftChild().getKey())>0)//uncle > cousin
            {
                rotateLeft(uncle);
                rotateLeft(grandparent);
                rotateRight(grandparent);
            }
            else
            {
                rotateLeft(grandparent);
                rotateRight(grandparent);
            }
        }
    }


}
