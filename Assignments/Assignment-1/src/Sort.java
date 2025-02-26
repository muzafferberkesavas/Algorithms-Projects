import java.util.Arrays;

public class Sort {

    public static void Insertion_Sort(int[] array){
        int length = array.length;
        for (int j = 1; j < length; j++){
            int element = array[j];
            int i = j - 1;
            while (i >= 0 && array[i] > element){
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = element;
        }
    }

    public static void Merge_Sort(int[] array){
        int length = array.length;
        if (length <= 1){
            return;
        }
        int middle = length / 2;
        int[] leftArray = Arrays.copyOfRange(array,0,middle);
        int[] rightArray = Arrays.copyOfRange(array,middle,array.length);
        Merge_Sort(leftArray);
        Merge_Sort(rightArray);
        Merge(array,leftArray,rightArray);
    }


    private static void Merge(int[] array,int[] first, int[] second){
        int index_1 = 0;
        int index_2 = 0;
        while (index_1 != first.length && index_2 != second.length){
            if (first[index_1] > second[index_2]){
                array[index_1 + index_2] = second[index_2++];
            } else{
                array[index_1 + index_2] = first[index_1++];
            }
        }
        while (index_1 != first.length){
            array[index_1 + index_2] = first[index_1++];
        }
        while (index_2 != second.length){
            array[index_1 + index_2] = second[index_2++];
        }
    }

    public static void Counting_Sort(int[] array){
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max){
                max = array[i];
            }
        }
        int[] count = new int[max+1];
        for (int i = 0; i <= max; i++){
            count[i] = 0;
        }
        int size = array.length;
        int[] output = new int[size];

        for (int j : array) {
            count[j] = count[j] + 1;
        }
        for (int i = 1; i <= max; i++){
            count[i] = count[i] + count[i-1];
        }
        for (int i = size - 1; i >= 0; i--){
            int j = array[i];
            output[count[j] - 1] = array[i];
            count[j] = count[j] - 1;
        }
        System.arraycopy(output, 0, array, 0, size);
    }

    public static boolean isSorted(int[] array){
        int length = array.length;
        boolean sorted = true;
        for (int i = 0; i < length - 1; i++){
            if (array[i] > array[i + 1]){
                sorted = false;
                break;
            }
        }
        return sorted;
    }

}
