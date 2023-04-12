package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class Autocomplete2 extends Autocomplete<List<String>> {

    public Autocomplete2(String dict_file, int max) {
        super(dict_file, max);
    }

    public List<String> getCandidates(String prefix) {
/*
        if(prefix!="") {

            boolean a = true;
            for (int i = 0; i < prefix.length(); i++) {
                if(prefix.charAt(i)!=' ')
                {
                    a = false;
                    break;
                }

            }

            if(a)
            {
                return List.of();
            }
        }

*/


        prefix = prefix.trim();

        //check if the prefix exist
        TrieNode<List<String>> node2 = find(prefix);
        List<String> empty = new ArrayList<>();

        if(node2==null)
        {
            return empty;}

        //pick candidate
        TrieNode<List<String>> node = find(prefix);

        if (!node.hasValue())
        node.setValue(bfs(node));

        if(node.getValue().size()<=getMax())
            return node.getValue();
        return node.getValue().subList(0,getMax());
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {

        TrieNode<List<String>> n = find(prefix);

        TrieNode<List<String>> n2 = find(candidate);

        //List<String> bb = new ArrayList<>();

        if(n == null) {
            put(prefix, List.of(candidate));
            TrieNode<List<String>> dummy = find(prefix);
            dummy.setEndState(false);
        }
            //bb.add(candidate);

        if(n2==null|| !n2.isEndState())
        {
            put(candidate,List.of());
        }

/*        if(bb.size()!=0)
        return;*/


        //if(!n.hasValue()) {n.setValue(bfs(n));}
        n = find(prefix);
        List<String> set = new ArrayList<>(n.getValue());

        Collections.reverse(set);
        set.add(candidate);
        set.remove(0);

        int q = 0;
        List<Integer> a = new ArrayList<>();

        for(int i = 0; i < set.size(); i++){
            if(set.get(i).equals(candidate))
            {a.add(i); }

        }

        if(a.size()==2)
        {   int b = a.get(0);
            set.remove(b);
        }

        Collections.reverse(set);
        n.setValue(set);
    }

    public List<String> bfs(TrieNode<List<String>> node) {

        Queue<TrieNode<List<String>>> queue = new LinkedList<>();
        queue.offer(node);
        List<String> output = new ArrayList<>();

        while (!queue.isEmpty()) {
            TrieNode<List<String>> currNode = queue.poll();

            if(output.size()==getMax()+2)
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
}
