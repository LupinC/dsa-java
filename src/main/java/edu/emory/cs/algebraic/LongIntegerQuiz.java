package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }
    /**
     * we have 2 longinteger: this and n
     * we first compare this and n and find the one with a larger absolute value
     * because I can only subtract a smaller one from a larger one
     * if abs(this) is larger
     * proceed as normal
     * otherwise, we flip the signs of both of them and subtract the smaller one from the larger one.
     * lastly, we took out excess zeros
     */
    @Override
    protected void addDifferentSign(LongInteger n){
        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m];

        if(this.compareAbs(n)>0)//this part subtract n from "this"
        {
            System.arraycopy(digits, 0, result, 0, digits.length);
            for (int i = 0; i < n.digits.length; i++) {
                result[i] -= n.digits[i];
                if (result[i] < 0) {
                    result[i] += n.digits[i];
                    result[i] += 10;
                    result[i] -= n.digits[i];
                    result[i + 1] -= 1;
                }
            }
            int c = 0;//finding how many unnecessary 0s are there
            for (int i = m-1; i > 0; i--) {
                if (result[i] == 0) {
                    c++;
                } else {
                    break;
                }
            }
            digits = result[m-1] == 0 ? Arrays.copyOf(result, m - c) : result;
        }
        else if (this.compareAbs(n)<0)
         //this part flip the signs first because the sign of the larger
         // one is what we need
         //so in this case then, subtract "this" from n
        {   if(sign == Sign.NEGATIVE)
            { sign = Sign.POSITIVE;
            n.sign = Sign.NEGATIVE;}
        else
        {    sign = Sign.NEGATIVE;
            n.sign = Sign.POSITIVE;}
            System.arraycopy(n.digits, 0, result, 0, n.digits.length);
            for (int i = 0; i < digits.length; i++) {
                result[i] -= digits[i];
                if (result[i] < 0) {
                    result[i] += digits[i];
                    result[i] += 10;
                    result[i] -= digits[i];
                    result[i + 1] -= 1;
                }
            }
            int c = 0;//finding how many unnecessary 0s are there
            for (int i = m-1; i > 0; i--) {
                if (result[i] == 0) {
                    c++;
                } else {
                    break;
                }
            }
            digits = result[m-1] == 0 ? Arrays.copyOf(result, m - c) : result;
        }
        else{//return POSITIVE 0 to make it look nicer if the absolute value of the two are the same
            byte[] a = {0};
            this.sign=Sign.POSITIVE;
            digits = a;
        }
    }
}
