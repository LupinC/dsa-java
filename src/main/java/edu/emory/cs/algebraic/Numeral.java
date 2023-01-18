package edu.emory.cs.algebraic;

/*public class Numeral {
    //reference types are objects but primitives are not
    //int 4 types

    public void add(Numeral n){/*cannot be implemented: what numeral is is not defined: is it an int? or is it a float?*/
    /*
    //2 types of abstract class, abstract class and interface
    //superclass so that u dont have to worry about using int or double


*/
public interface Numeral<T extends Numeral<T>>  {
    void add(T n);

}

