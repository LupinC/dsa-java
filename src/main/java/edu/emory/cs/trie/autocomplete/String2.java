package edu.emory.cs.trie.autocomplete;

public class String2 {
    String s;
    int frequency;
    int recency;

    public String2(String input)
    {
        s = input;
        frequency = 1;

    }

    public String getString(){
        return s;

    }
    public void setRecency(int i){
        recency = i;
    }

    public String getValue(){
        return s;
    }

    public int getFrequency(){
        return frequency;

    }

    public int getRecency(){
        return recency;
    }

    public int compareTo(String2 a) {
        if (this.recency > a.recency) {
            return 1;
        } else if (this.recency == a.recency) {
            if (this.frequency > a.frequency) {
                return 1;
            } else if (this.frequency == a.frequency) {
                if(this.s.compareTo(a.s) > 0) {
                    return 1;
                } else if (this.s.compareTo(a.s) == 0) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
        else
        {
            return -1;
        }

    }
}
