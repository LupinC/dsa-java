package edu.emory.cs.trie.autocomplete;

import org.junit.jupiter.api.Test;

import java.util.List;

public class AutocompleteTest {

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 10;

        Autocomplete<?> ac = new AutocompleteHW(dict_file, max);
        List<String> a = ac.getCandidates("sh");
        for(String c : a)
        {
            System.out.print(c+" ");
        }

        System.out.println();

        List<String> b = ac.getCandidates("abs");
        ac.pickCandidate("abs","absddd");
        ac.pickCandidate("abs", "absdddd");
        ac.pickCandidate("abs","absddda");
        ac.pickCandidate("abs","absn");
        ac.pickCandidate("abs","absorb");
        List<String> d = ac.getCandidates("abs");

        for(String c : d)
        {
            System.out.print(c+" ");
        }

        System.out.println();

        List<String> e = ac.getCandidates("absddd");

        for (String c: e)
        {
            System.out.print(c + " ");
        }


    }


}
