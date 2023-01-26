package edu.emory.cs.algebraic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LongIntegerQuizTest {
    @Test
    public void test0() {
        LongInteger a = new LongIntegerQuiz("875");
        a.add(new LongIntegerQuiz("0"));
        assertEquals("875", a.toString());
    }

    @Test
    public void test2() {
        LongInteger a = new LongIntegerQuiz("789");
        a.add(new LongIntegerQuiz("-0"));
        assertEquals("789", a.toString());
    }

    @Test
    public void test3() {
        LongInteger a = new LongIntegerQuiz("875");
        a.add(new LongIntegerQuiz("-875"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test4() {
        LongInteger a = new LongIntegerQuiz("-875");
        a.add(new LongIntegerQuiz("875"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test5() {
        LongInteger a = new LongIntegerQuiz("714");
        a.add(new LongIntegerQuiz("-713"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test6() {
        LongInteger a = new LongIntegerQuiz("-714");
        a.add(new LongIntegerQuiz("713"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test7() {
        LongInteger a = new LongIntegerQuiz("713");
        a.add(new LongIntegerQuiz("-714"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test8() {
        LongInteger a = new LongIntegerQuiz("-713");
        a.add(new LongIntegerQuiz("714"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test9() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-458793"));
        assertEquals("-458670", a.toString());
    }

    @Test
    public void test10() {
        LongInteger a = new LongIntegerQuiz("-87654");
        a.add(new LongIntegerQuiz("123"));
        assertEquals("-87531", a.toString());
    }
}
