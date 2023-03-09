package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {

    public List<Entity> getEntities(String input) {

        List<Entity> entities = new ArrayList<>();

        TrieNode<Integer> node = getRoot();// get the item from the top of the given trie
        int start = 0;//begin index

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            node = node.getChild(c);//so it will be c1c2c3...

            if (node == null) {
                node = getRoot();
                start = 1;//finished with this letter c1 c2 c3 ... that one of ci, i = 1:c_input.length()-1 is not a child from this node

            } else if (node.isEndState()) {//found it

                entities.add(new Entity(start+1, i + 1, node.getValue()));
            }

            if (start == 1) {
                start = i;// new begin index
            }
        }

        return entities;
    }
}
