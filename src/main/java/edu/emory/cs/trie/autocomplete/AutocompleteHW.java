package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<List<String>> {

    List<String> candidate = new ArrayList<>();
    List<String> child = new ArrayList<>();

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        candidate.clear();

        TrieNode<List<String>> node = find(prefix);

        if(node.getValue()==null)
        node.setValue(bfs(node));

        if(!node.hasValue())
            node.setValue(bfs(node));

        if(node.getValue().size()==1)
            node.setValue(bfs(node));


        if(node.getValue().size()<getMax())
        {
            for (int i = 0; i < node.getValue().size(); i++)
            {
                candidate.add(node.getValue().get(i));
            }
        }

        else {
        for(int i = 0; i< getMax(); i++)
        {
            candidate.add(node.getValue().get(i));
        }}
        //System.out.println(getMax());
        return candidate;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {

        TrieNode<List<String>> node = find(prefix);

        List<String> empty = new ArrayList<>();
        empty.add(candidate);

        if(!node.hasValue()) {
            node.setValue(getCandidates(prefix));
        }

        for(int i = 0; i < node.getValue().size(); i++)
        {
            if(node.getValue().get(i).equals(candidate))
            {
                String t = node.getValue().get(i);
                node.getValue().remove(i);
                List<String> temp = node.getValue();
                Collections.reverse(temp);
                temp.add(t);
                temp.remove(0);
                Collections.reverse(temp);
                node.setValue(temp);
                return;
            }
        }

        TrieNode<List<String>> node2 = find(candidate);

        if(node2 == null)
        {
            put(candidate,empty);

        }

        TrieNode<List<String>> node3 = find(candidate);

        node3.setValue(bfs(node3));

        List<String> temp = node.getValue();
        Collections.reverse(temp);
        temp.add(candidate);
        temp.remove(0);
        Collections.reverse(temp);
        node.setValue(temp);

    }

    public List<String> dfs(TrieNode<List<String>> node) {

        if(node.isEndState()){
            child.add(word(node));
        }

        for (TrieNode<List<String>> c : node.getChildrenMap().values()) {
            dfs(c);
        }

        return child;
    }

    public List<String> bfs(TrieNode<List<String>> node) {

        Queue<TrieNode<List<String>>> queue = new LinkedList<>();

        //PriorityQueue<TrieNode<List<String>>> queue = new PriorityQueue<>(Comparator.comparing(TrieNode::getKey));
        queue.offer(node);

        List<String> output = new ArrayList<>();

        while (!queue.isEmpty()) {
            TrieNode<List<String>> currNode = queue.poll();

            if (currNode.isEndState()) {
                output.add(word(currNode));
                output = sortStrings(output);
            }

            for (TrieNode<List<String>> childs : currNode.getChildrenMap().values()) {
                queue.offer(childs);
            }
        }

        return output;
    }


    public String word(TrieNode<List<String>> node){

        List<Character> reversed = new ArrayList<>();

        char c = node.getKey();

        reversed.add(c);

        while(node.getParent()!=getRoot())
        {
            node = node.getParent();
            char d = node.getKey();
            reversed.add(d);
        }

        char[] reverse = new char[reversed.size()];

        for(int i = 0; i < reverse.length; i++)
        {
            reverse[i] = reversed.get(i);
        }

        for (int i = 0; i< reverse.length/2;i++)
        {
            char temp = reverse[i];
            reverse[i] = reverse[reverse.length-1-i];
            reverse[reverse.length-1-i]=temp;
        }

        String s = "";

        for (int i = 0; i < reverse.length;i++)
        {
            s= s+reverse[i];
        }

        return s;

    }

    public List<String> sortStrings(List<String> strings) {
        strings.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        return strings;
    }
}
