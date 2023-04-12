package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class Autocomplete4 extends Autocomplete<List<String>> {

    List<String> candidate = new ArrayList<>();
    List<String> child = new ArrayList<>();


    //getCandidates("") will work, but it will take more than 5 mins to load
    public Autocomplete4(String dict_file, int max) {
        super(dict_file, max);
    }

    public List<String> getCandidates(String prefix) {
        candidate.clear();
        //check if the prefix exist
        prefix = prefix.trim();
        if(prefix.equals(""))
        {
            TrieNode<List<String>> node2 = find(prefix);
            return bfs2(node2).subList(0,getMax());
        }

        TrieNode<List<String>> node2 = find(prefix);
        List<String> empty = new ArrayList<>();


        if(node2==null)
            return empty;

        //pick candidate
        TrieNode<List<String>> node = find(prefix);

        if(node.getValue()==null)
            node.setValue(bfs(node));

        if(node.getValue().size()==0)
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

    public boolean containPrefix(String prefix, String candidate)
    {

        if(candidate.length()<prefix.length()){return  false;}
        for(int i = 0; i < prefix.length(); i++)
        {
            if(prefix.charAt(i)!=candidate.charAt(i)){return false;}
        }
        return true;
        //
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {

        if(prefix.equals(""))
        {
            TrieNode<List<String>> node = find(prefix);
            node.setValue(bfs2(node));
        }


        //prefix does not exist, candidate does not exist
        TrieNode<List<String>> node = find(prefix);

        if(node == null){
            List<String> empty = new ArrayList<>();
            empty.add(candidate);
            put(prefix,empty);
            TrieNode<List<String>> c = find(prefix);
            c.setEndState(false);

            //candidate have the prefix
            if(!containPrefix(prefix,candidate))
            {
                put(candidate,List.of());
            }
            //candidate does not have prefix

        }

        node = find(prefix);
        if(!node.hasValue()) {
            node.setValue(bfs(node));
        }

        //prefix exist, candidate exist
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

        //prefix exist, candidate does not
        TrieNode<List<String>> node2 = find(candidate);

        if(node2 == null)
        {
            List<String> empty = new ArrayList<>();
            empty.add(candidate);
            put(candidate,empty);
        }

        TrieNode<List<String>> node3 = find(prefix);

        List<String> temp = node3.getValue();
        Collections.reverse(temp);
        temp.add(candidate);
        Collections.reverse(temp);
        node3.setValue(temp);

    }

    public List<String> bfs(TrieNode<List<String>> node) {

        Queue<TrieNode<List<String>>> queue = new LinkedList<>();
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
        for (int i = 0; i < reverse.length;i++) {s = s + reverse[i];}
        return s;

    }

    public List<String> sortStrings(List<String> strings) {
        strings.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        return strings;
    }

    public List<String> bfs2(TrieNode<List<String>> node) {

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
}
