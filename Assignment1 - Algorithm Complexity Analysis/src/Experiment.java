import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

public class Experiment {
    public static void allExperiments(int[] data) throws IOException{
        experimentSorting(data, "Random");

        int[] sortedData = MergeSort.mergeSort(data);

        experimentSorting(sortedData, "Sorted");

        int[] reverselySortedData = sortedData.clone();
        for (int i = 0; i < reverselySortedData.length / 2; i++) {
            int temp = reverselySortedData[i];
            reverselySortedData[i] = reverselySortedData[reverselySortedData.length - i - 1];
            reverselySortedData[reverselySortedData.length - i - 1] = temp;
        }
        experimentSorting(data, "Reversely Sorted");

        experimentSearching(data, sortedData);
    }

    public static void experimentSorting(int[] data, String dataType) throws IOException{
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000};
        double[] averagesOfInsertion = new double[inputSizes.length];
        double[] averagesOfMerge = new double[inputSizes.length];
        double[] averagesOfCounting = new double[inputSizes.length];

        for (int i = 0; i < inputSizes.length; i++){
            int[] subarray = Arrays.copyOfRange(data, 0, inputSizes[i]);
            averagesOfInsertion[i] = InsertionSort.insertionSortAverage(subarray);
            averagesOfMerge[i] = MergeSort.mergeSortAverage(subarray);
            averagesOfCounting[i] = CountingSort.countingSortAverage(subarray);
        }
        print(inputSizes, averagesOfInsertion, "insertion sort", dataType);
        print(inputSizes, averagesOfMerge,"merge sort", dataType);
        print(inputSizes, averagesOfCounting,"counting sort", dataType);

        double[][] yaxis = new double[3][inputSizes.length];
        yaxis[0] = averagesOfInsertion;
        yaxis[1] = averagesOfMerge;
        yaxis[2] = averagesOfCounting;
        String[] legend = {"Insertion Sort", "Merge Sort", "Counting Sort", "Milliseconds"};
        showAndSaveChart("Sorting Algorithms on "+ dataType +" Data", inputSizes, yaxis, legend);
    }
    public static void experimentSearching(int[] randomData, int[] sortedData) throws IOException {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        double[] averagesOfRandomLinear = new double[inputSizes.length];
        double[] averagesOfSortedLinear = new double[inputSizes.length];
        double[] averagesOfBinary = new double[inputSizes.length];

        for (int i = 0; i < inputSizes.length; i++){
            int[] subarrayOfRandom = Arrays.copyOfRange(randomData, 0, inputSizes[i]);
            int[] subarrayOfSorted = Arrays.copyOfRange(sortedData, 0, inputSizes[i]);

            int x = randomData[(inputSizes[i])/3];
            averagesOfRandomLinear[i] = Search.linearSearchAverage(subarrayOfRandom, x);
            averagesOfSortedLinear[i] = Search.linearSearchAverage(subarrayOfSorted, x);
            averagesOfBinary[i] = Search.binarySearchAverage(subarrayOfSorted, x);
        }

        print(inputSizes, averagesOfRandomLinear, "linear search", "random");
        print(inputSizes, averagesOfSortedLinear, "linear search", "sorted");
        print(inputSizes, averagesOfBinary, "binary search", "sorted");
        double[][] yaxis = new double[3][inputSizes.length];
        yaxis[0] = averagesOfRandomLinear;
        yaxis[1] = averagesOfSortedLinear;
        yaxis[2] = averagesOfBinary;
        String[] legend = {"Linear Search on Random", "Linear Search on Sorted", "Binary Search on Sorted", "Nanoseconds"};
        showAndSaveChart("Searching Algorithms", inputSizes, yaxis, legend);
    }

    public static void print(int[] inputSize, double[] results, String sortingType, String dataType){
        System.out.println(sortingType.toUpperCase() + " WITH "+ dataType.toUpperCase() + " DATA");
        for (int i = 0; i < inputSize.length; i++){
            int val = (int) Math.round(results[i]);
            System.out.printf("Average Running Time with input of %-6s: %s ms\n", inputSize[i], results[i]);
        }
    }
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis, String[] legend) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in " + legend[3] + "").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries(legend[0], doubleX, yAxis[0]);
        chart.addSeries(legend[1], doubleX, yAxis[1]);
        chart.addSeries(legend[2], doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
