package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



public class HybridSortHW <T extends Comparable<T>> implements HybridSort<T>{

    @Override
    @SuppressWarnings("unchecked")
    public T[] sort(T[][] input) {
        List<T[]> rows = new ArrayList<T[]>();


        for(int i = 0; i < input.length; i++){


            if (isAscending(input[i]))
            {/* AbstractSort<T> engine = new InsertionSort<>();
            engine.sort(input[i],0,input[i].length);*/

                }
            else if(isDescending(input[i]))
            {
                //AbstractSort<T> engine = new ShellSortKnuth<>();
                //engine.sort(input[i],0,input[i].length);
                reverse(input[i]);

            }
            else
            {
                AbstractSort<T> engine = new QuickSort<>();
                engine.sort(input[i], 0,input[i].length);

            }

            rows.add(input[i]);

        }


        T[] output = merge2(rows);


/*        for (int i = 0; i< output.length;i++){
            System.out.print(output[i]+" ");
        }*/

        return output;
    }

    public boolean isAscending(T[] row){
        for(int i = 0; i< row.length-1;i++){
            if(row[i].compareTo(row[i+1])>0){
                return false;
            }
        }

        return true;
    }


    public boolean isDescending(T[] row){
        for(int i = 0; i< row.length-1;i++){
            if(row[i].compareTo(row[i+1])<0){
                return false;
            }
        }

        return true;
    }


    @SuppressWarnings("unchecked")
    public T[] merge(T[] one, T[] two) {

        T[] three = (T[]) Array.newInstance(one[0].getClass(),one.length+two.length);
        int len = three.length;

        int indexone = 0;
        int indextwo = 0;

        for(int i = 0; i < len; i++)
        {
            if(indexone<one.length&&indextwo<two.length)
            {
                if(one[indexone].compareTo(two[indextwo])<=0)
                {   three[i]=one[indexone];
                    indexone++;}
                else
                {   three[i]=two[indextwo];
                    indextwo++;}
            }
            else
            {
                if(indextwo<two.length){
                    three[i]=two[indextwo];
                    indextwo++;
                }
                if(indexone<one.length){
                    three[i]=one[indexone];
                    indexone++;
                }
            }
        }

        return three;

    }

    public T[] merge2(List<T[]> rows)
    {
        int size = rows.size();
        if (size == 1){
            return rows.get(0);
        }
        else if(size == 2){
            return merge(rows.get(0),rows.get(1));
        }
        else
        {
            int mid = 0 + (rows.size()-0)/2;
            List<T[]> left = rows.subList(0,mid);
            List<T[]> right = rows.subList(mid, size);
            T[] left2 = merge2(left);
            T[] right2 = merge2(right);
            return merge(left2,right2);
        }
    }

    public void reverse(T[] row){

        int left = 0;
        int right = row.length-1;

        while(left<=right){
            T temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }


}
