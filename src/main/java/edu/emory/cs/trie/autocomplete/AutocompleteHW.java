package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {

    List<String> candidates = new ArrayList<>();

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {

        TrieNode<List<String>> node = find(prefix);

        if (node == null) {
            return candidates;
        }
        
        if (node.isEndState()) {
            candidates.add(prefix);
        }

        List<TrieNode<List<String>>> nodes = new ArrayList<>();
        nodes.add(node);

        int count = 0;
        while (!nodes.isEmpty() && count < getMax()) {
            TrieNode<List<String>> current = nodes.remove(nodes.size() - 1);
            if (current.isEndState()) {
                candidates.add(current.getValue().get(0));
                count++;
            }
            nodes.addAll(current.getChildrenMap().values());
        }

        Collections.reverse(candidates);
        return candidates;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        List<String> candidates = get(prefix);
        if (candidates != null && candidates.contains(candidate)) {
            candidates.remove(candidate);
            candidates.add(0, candidate);
        }
    }
}
