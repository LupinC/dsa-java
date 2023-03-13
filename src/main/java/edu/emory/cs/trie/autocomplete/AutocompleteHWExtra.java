package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHWExtra extends Autocomplete<List<String>> {
    private final Map<String, List<String>> selected;


    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
        selected = new HashMap<>();

    }

    @Override
    public List<String> getCandidates(String prefix) {
        List<String> candidates = new ArrayList<>();
        TrieNode<List<String>> node = find(prefix);

        if (node != null) {
            collect(node, candidates, getMax());
        }

        List<String> recent = selected.get(prefix);
        if (recent != null) {
            candidates.removeAll(recent);
            candidates.addAll(0, recent);
        }

        return candidates;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        List<String> recent = selected.get(prefix);
        if (recent == null) {
            recent = new ArrayList<>();
            selected.put(prefix, recent);
        }
        recent.remove(candidate);
        recent.add(0, candidate);
        if (recent.size() > getMax()) {
            recent.remove(getMax());
        }
    }

    private void collect(TrieNode<List<String>> node, List<String> candidates, int max) {
        if (node.isEndState()) {
            candidates.add(node.getValue().get(0));
            if (candidates.size() >= max) {
                return;
            }
        }

        for (TrieNode<List<String>> child : node.getChildrenMap().values()) {
            collect(child, candidates, max);
            if (candidates.size() >= max) {
                return;
            }
        }
    }
}