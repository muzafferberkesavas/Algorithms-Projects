public class Search {
    public static int Linear_Search(int[] array, int val){
        int size = array.length;
        for(int i = 0; i < size; i++){
            if (array[i] == val){
                return i;
            }
        }
        return -1;
    }

    public static int Binary_Search(int[] sortedArray, int val){
        int low = 0;
        int high = sortedArray.length - 1;
        while (high - low > 1){
            int mid = (high + low) / 2;
            if (sortedArray[mid] < val){
                low = mid + 1;
            }else {
                high = mid;
            }
        }
        if (sortedArray[low] == val){
            return low;
        }else if (sortedArray[high] == val){
            return high;
        }
        return -1;
    }
}
