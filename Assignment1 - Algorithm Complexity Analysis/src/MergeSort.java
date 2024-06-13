public class MergeSort {
    public static int[] mergeSort(int[] A) {
        int n = A.length;
        if (n <= 1)
            return A;
        int[] left = new int[n / 2];
        int[] right = new int[n - n / 2];
        System.arraycopy(A, 0, left, 0, n / 2);
        System.arraycopy(A, n / 2, right, 0, n - n / 2);
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }
    public static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        int i = 0, j = 0, k = 0;
        while (i < A.length && j < B.length) {
            if (A[i] > B[j])
                C[k++] = B[j++];
            else
                C[k++] = A[i++];
        }
        while (i < A.length)
            C[k++] = A[i++];
        while (j < B.length)
            C[k++] = B[j++];
        return C;
    }

    public static double mergeSortAverage(int[] data){
        long totalTime = 0;
        for (int i = 0; i < 10 ; i++){
            int[] copyOfData = data.clone();
            long startTime = System.currentTimeMillis();
            mergeSort(copyOfData);
            long endTime = System.currentTimeMillis();
            totalTime += (endTime-startTime);
        }
        double averageTimeDouble = (double) totalTime / 10;
        return averageTimeDouble;
    }
}
