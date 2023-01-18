package edu.emory.cs.algebraic;

public interface SignedNumeral< T extends SignedNumeral<T>> extends Numeral<T>{
    /** flips the sign of this numeral.*/

    void flipSign();

    default void subtract(T n){
        n.flipSign();
        add(n);
        n.flipSign();
    }

}
