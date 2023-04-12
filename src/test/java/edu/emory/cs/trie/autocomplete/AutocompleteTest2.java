package edu.emory.cs.trie.autocomplete;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.emory.cs.trie.TrieNode;

public class AutocompleteTest2 {
    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 10;

        Autocomplete<?> ac = new Autocomplete2(dict_file, max);
        Eval eval = new Eval();
        testAutocomplete(ac, eval);
    }

    private void testAutocomplete(Autocomplete<?> ac, Eval eval) {
        String prefix;
        List<String> expected;

        prefix = "sh";
        expected = List.of("sh", "sha", "she", "shh", "shi", "sho", "shp", "shr", "sht", "shu");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "sh";
        expected = List.of("sh", "sha", "she", "shh", "shi", "sho", "shp", "shr", "sht", "shu");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "sh";
        expected = List.of("shu", "sh", "sha", "she", "shh", "shi", "sho", "shp", "shr", "sht");
        ac.pickCandidate(prefix, "shu");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "sh";
        expected = List.of("sha", "shu", "sh", "she", "shh", "shi", "sho", "shp", "shr", "sht");
        ac.pickCandidate(prefix, "sha");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "xtx";
        expected = List.of();
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "sh";
        expected = List.of("xxx", "sha", "shu", "sh", "she", "shh", "shi", "sho", "shp", "shr");
        ac.pickCandidate(prefix, "xxx");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "sh";
        expected = List.of("shx", "xxx", "sha", "shu", "sh", "she", "shh", "shi", "sho", "shp");
        ac.pickCandidate(prefix, "shx");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "jinhoo";
        ac.pickCandidate(prefix, "jinho");
        List<String> a = ac.getCandidates(prefix);
        System.out.println(a);

        prefix = "jin";
        a = ac.getCandidates(prefix);
        System.out.println(a);

        prefix = "";
        ac.pickCandidate(prefix,"moon");
        a = ac.getCandidates(prefix);
        System.out.println(a);

        prefix = "nonsubs";
        ac.pickCandidate(prefix, "nonsubsidi");
        List<String> b = ac.getCandidates(prefix);
        System.out.println(b);

        prefix = "nonsubsid";
        b = ac.getCandidates(prefix);
        System.out.println(b);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);


        TrieNode<?> nodeXtx = ac.find("xtx");
        TrieNode<?> nodeXxx = ac.find("xxx");
        TrieNode<?> nodeShx = ac.find("shx");
        TrieNode<?> nodeBlah = ac.find("xcw");

        System.out.printf("Are the following candidates or prefixes added to the trie?\n");
        System.out.printf("xtx: %b\n", nodeXtx != null);
        System.out.printf("xxx: %b\n", nodeXxx != null);
        System.out.printf("shx: %b\n", nodeShx != null);
        System.out.printf("xcw: %b\n", nodeBlah != null);
    }

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            } else
                log += String.format("FAIL -> expected = %s, returned = %s", expected, candidates);
        } catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
}
