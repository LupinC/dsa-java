package edu.emory.cs.trie.autocomplete;

import org.junit.jupiter.api.Test;

import java.util.List;

public class AutocompleteTest {

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 21;

        Autocomplete<List<String>> ac = new AutocompleteHW(dict_file, max);
        List<String> a = ac.getCandidates(" sh ");
        for(String c : a)
        {
            System.out.print(c+" ");
        }
        System.out.println();



        List<String> b = ac.getCandidates("abs");
        for(String c : b)
        {
            System.out.print(c+" ");
        }
        System.out.println();


        List<String> g = ac.getCandidates("");
        for(String c : g)
        {
            System.out.print(c+" ");
        }
        System.out.println();

        ac.pickCandidate("abs","absddd");
        ac.pickCandidate("abs", "absdddd");
        ac.pickCandidate("abs","absddda");
        ac.pickCandidate("abs","absn");
        ac.pickCandidate("abs","absorb");
        ac.pickCandidate("abs","dump");
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
        System.out.println();


        //case: both prefix and candidate does not exist
        ac.pickCandidate("m","moon");
        List<String> f = ac.getCandidates("m");
        //System.out.println(ac.find("moon").getValue().get(0));
        for (String c: f)
        {
            System.out.print(c + " ");
        }



    }


}
