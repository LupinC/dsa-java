package edu.emory.cs.trie;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrieQuizTest {
    @Test
    public void testGetEntities() {
        final List<String> L = List.of("United States", "South Korea");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        String input = "I was born in South Korea and raised in the United States";
        List<Entity> entities = List.of(new Entity(44, 57, 0), new Entity(14, 25, 1));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities1() {
        final List<String> L = List.of("United States", "South Korea", "Japan", "Canada");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);
        String input = "flCanadafl South Korea abcUnited States. Canada. fJapanSouth Korea.";
        List<Entity> entities = List.of(
                new Entity(26, 39, 0),
                new Entity(11, 22, 1),
                new Entity(50, 55, 2),
                new Entity(2, 8, 3),
                new Entity(41, 47, 3),
                new Entity(55, 66, 1));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }


    @Test
    public void testGetEntities2() {
        final List<String> L = List.of("United States", "Japan", "Canada");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);
        String input = "United States. United. States. United Nation.";
        List<Entity> entities = List.of(
                new Entity(0, 13, 0));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}
