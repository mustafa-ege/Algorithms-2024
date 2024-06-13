public class CountingSort {
    public static int[] countingSort(int[] A) {
        int size = A.length;
        int k = 0;
        for (int i = 0; i < size; i++) {
            k = Math.max(k, A[i]);
        }
        int[] count = new int[k + 1];
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            int j = A[i];
            count[j]++;
        }
        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }
        for (int i = size - 1; i >= 0; i--) {
            int j = A[i];
            output[count[j] - 1] = A[i];
            count[j]--;
        }
        return output;
    }

    public static double countingSortAverage(int[] data){
        long totalTime = 0;
        for (int i = 0; i < 10 ; i++){
            int[] copyOfData = data.clone();
            long startTime = System.currentTimeMillis();
            countingSort(copyOfData);
            long endTime = System.currentTimeMillis();
            totalTime += (endTime-startTime);
        }
        double averageTimeDouble = (double) totalTime / 10;
        return averageTimeDouble;
    }
}
