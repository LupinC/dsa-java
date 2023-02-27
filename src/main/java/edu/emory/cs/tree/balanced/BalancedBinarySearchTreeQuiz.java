package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.AbstractBinaryNode;
import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {

        BinaryNode parent = node.getParent();
        BinaryNode grandparent = node.getGrandParent();
        BinaryNode uncle = node.getUncle();


        if(node.getParent().getKey().compareTo(node.getKey())>0)
        {
            if(uncle.getKey().compareTo(uncle.getLeftChild().getKey())>0)//uncle > cousin
            {
                rotateRight(node);
                rotateLeft(grandparent);
                rotateRight(uncle);
            }

            else
            {
                rotateRight(node);
                rotateLeft(grandparent);
                rotateLeft(uncle);
                rotateRight(uncle);
            }
        }
        else
        {
            if(uncle.getKey().compareTo(uncle.getLeftChild().getKey())>0)//uncle > cousin
            {
                rotateLeft(grandparent);
                rotateLeft(uncle);
            }
            else
            {
                rotateLeft(grandparent);
                rotateRight(uncle);
            }
        }
    }


}
