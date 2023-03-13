package edu.emory.cs.trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNode<T> {
    private final Map<Character, TrieNode<T>> children;
    private TrieNode<T> parent;
    private boolean end_state;
    private char key;
    private T value;

    public TrieNode(TrieNode<T> parent, char key) {
        children = new HashMap<>();
        setEndState(false);
        setParent(parent);
        setKey(key);
        setValue(null);
    }

    public TrieNode<T> getParent() { return parent; }

    public char getKey() { return key; }

    public T getValue() { return value; }

    public TrieNode<T> getChild(char key) { return children.get(key); }

    /** @return the map whose keys and values are children's characters and nodes. */
    public Map<Character, TrieNode<T>> getChildrenMap() {
        return children;
    }

    public void setParent(TrieNode<T> node){ this.parent = node; }

    public void setKey(char key){this.key = key;}

    public void setEndState(boolean endState) {
        end_state = endState;
    }

    public T setValue(T value){
        T tmp = this.value;
        this.value = value;
        return tmp;
    }

    public TrieNode<T> addChild(char key){
        TrieNode<T> child = getChild(key);

        if(child == null)
        {
            child = new TrieNode<T>(this,key);
            children.put(key,child);

        }

        return child;
    }

    public TrieNode<T> removeChild(char key){
        return children.remove(key);
    }

    public boolean isEndState(){return end_state;}

    public boolean hasValue(){ return value!=null;}

    public boolean hasChildren(){return !children.isEmpty();}





}
