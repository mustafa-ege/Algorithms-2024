public class InsertionSort {

    public static void insertionSort(int[] A) {
        for (int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i--;
            }
            A[i + 1] = key;
        }
    }
    public static double insertionSortAverage(int[] data){
        long totalTime = 0;
        for (int i = 0; i < 10 ; i++){
            int[] copyOfData = data.clone();
            long startTime = System.currentTimeMillis();
            insertionSort(copyOfData);
            long endTime = System.currentTimeMillis();
            totalTime += (endTime-startTime);
        }
        double averageTimeDouble = (double) totalTime / 10;
        return averageTimeDouble;
    }




}
