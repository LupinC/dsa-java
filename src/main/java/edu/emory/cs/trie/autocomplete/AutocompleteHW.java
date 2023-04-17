package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<List<String>> {

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    public List<String> getCandidates(String prefix) {

        prefix = prefix.trim();
        prefix = transformToLetters(prefix);

        //check if the prefix exist
        TrieNode<List<String>> node2 = find(prefix);
        List<String> empty = new ArrayList<>();

        if(node2==null)
        {return empty;}

        //pick candidate
        TrieNode<List<String>> node = find(prefix);

        List<String> candidate = new ArrayList<>(bfs(node));
        List<String> stored;
        if(node.getValue()!=null) stored = new ArrayList<>(node.getValue());
        else stored= new ArrayList<>();

        if(!stored.isEmpty()){
            for(String s: stored){
                for(int i = candidate.size()-1; i >= 0 ; i--){
                    if(s.equals(candidate.get(i)))
                    {candidate.remove(i); }
                }}

            candidate.addAll(0,stored);}

        if(candidate.size()<=getMax())
            return candidate;
        return candidate.subList(0,getMax());
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        candidate = candidate.trim();

        TrieNode<List<String>> n = find(prefix);
        TrieNode<List<String>> n2 = find(candidate);

        if(n == null&&n2 == null)
        {
            put(prefix,List.of(candidate));
            TrieNode<List<String>> node = find(prefix);
            node.setEndState(false);
            put(candidate,List.of());
            return;
        }

        if (n == null) {
            List<String> list = List.of(candidate);
            //check if cand exist
            TrieNode<List<String>> node = find(candidate);
            if(node==null)
                put(candidate,List.of());
            else
                node.setEndState(true);

            put(prefix, list);
            n = find(prefix);
            n.setEndState(false);
            return;
        }

        if(n2!=null&& !n2.isEndState())
        {
            n2.setEndState(true);
        }

        if(n2==null|| !n2.isEndState())
        {
            put(candidate,List.of());
        }

        n = find(prefix);
        List<String> set;
        if(n.getValue()==null)
            set = new ArrayList<>();
        else
            set = new ArrayList<>(n.getValue());
        Collections.reverse(set);
        set.add(candidate);
        Collections.reverse(set);
        n.setValue(set);
    }

    public List<String> bfs(TrieNode<List<String>> node) {

        int count = 0;

        if(node.getValue()!=null)
        {count = node.getValue().size();}

        Queue<TrieNode<List<String>>> queue = new LinkedList<>();
        queue.offer(node);
        List<String> output = new ArrayList<>();

        while (!queue.isEmpty()) {
            TrieNode<List<String>> currNode = queue.poll();

            if(output.size()==getMax()+count)
            {
                break;
            }
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
        for (int i = 0; i < reverse.length;i++) {s = s + reverse[i];}
        return s;

    }

    public List<String> sortStrings(List<String> strings) {
        strings.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        return strings;
    }

    public static String transformToLetters(String input) {
        // Initialize an empty string to store the transformed output
        String output = "";

        // Loop through each character in the input string
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // If the character is a letter, add it to the output string
            if (Character.isLetter(c)) {
                output += c;
            }
        }

        // Return the transformed output string
        return output;
    }
}
