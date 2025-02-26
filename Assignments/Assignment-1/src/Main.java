import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        String fileName = args[0];

        try{
            File file = new File("src/"+fileName);
            String csvFile = file.getAbsolutePath();


            int targetColumn = 6;
            List<Integer> valuesList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] valuesStr = line.split(",");
                    if (valuesStr.length > targetColumn) {
                        int value = Integer.parseInt(valuesStr[targetColumn]);
                        valuesList.add(value);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            int[] valuesArray = new int[valuesList.size()];

            for (int i = 0; i < valuesList.size(); i++) {
                valuesArray[i] = valuesList.get(i);
            }
            print(valuesArray);
        }catch (Exception e1){
            File file = new File(fileName);
            String csvFile = file.getAbsolutePath();


            int targetColumn = 6;
            List<Integer> valuesList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] valuesStr = line.split(",");
                    if (valuesStr.length > targetColumn) {
                        int value = Integer.parseInt(valuesStr[targetColumn]);
                        valuesList.add(value);
                    }

                }

            } catch (IOException e2) {
                e2.printStackTrace();
            }

            int[] valuesArray = new int[valuesList.size()];

            for (int i = 0; i < valuesList.size(); i++) {
                valuesArray[i] = valuesList.get(i);
            }
            print(valuesArray);
        }
    }
    public static void reverseArray(int[] array){
        int length = array.length;
        for (int i = 0; i < length / 2; i++){
            int temp = array[i];
            array[i] = array[length - i - 1];
            array[length -i - 1] = temp;
        }
    }

    public static void print(int[] valuesArray) throws IOException{

        int[] sizes = {500,1000,2000,4000,8000,16000,32000,64000,128000,250000};


        int[][] sortedArrays = new int[sizes.length][];

        double[][] unsortedMs = new double[3][10];
        double[][] sortedMs = new double[3][10];
        double[][] reverselySortedMs = new double[3][10];
        double[][] searchNs = new double[3][10];

        int index = 0;
        int m = 0;
        System.out.println("INSERTION SORT WITH RANDOM DATASETS");

        for (int size : sizes){
            int[] sorted = new int[size];
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = Arrays.copyOfRange(valuesArray,0,sizes[index]);
                long startTime = System.currentTimeMillis();
                Sort.Insertion_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
                if (i == 9){
                    sorted = temp;
                }
            }
            sortedArrays[index] = sorted;
            averageTime = (double) totalTime / 10;
            unsortedMs[0][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }
        index = 0;
        m = 0;
        System.out.println("MERGE SORT WITH RANDOM DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = Arrays.copyOfRange(valuesArray,0,sizes[index]);
                long startTime = System.currentTimeMillis();
                Sort.Merge_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            unsortedMs[1][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }
        index = 0;
        m = 0;
        System.out.println("COUNTING SORT WITH RANDOM DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = Arrays.copyOfRange(valuesArray,0,sizes[index]);
                long startTime = System.currentTimeMillis();
                Sort.Counting_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            unsortedMs[2][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        showAndSaveChart_1("Sorting Tests on Random Data", sizes, unsortedMs);
        index = 0;
        m = 0;
        System.out.println("INSERTION SORT WITH SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                long startTime = System.currentTimeMillis();
                Sort.Insertion_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            sortedMs[0][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        index = 0;
        m = 0;
        System.out.println("MERGE SORT WITH SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                long startTime = System.currentTimeMillis();
                Sort.Merge_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            sortedMs[1][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        index = 0;
        m=0;
        System.out.println("COUNTING SORT WITH SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                long startTime = System.currentTimeMillis();
                Sort.Counting_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            sortedMs[2][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        showAndSaveChart_1("Sorting Tests on Sorted Data", sizes, sortedMs);
        index = 0;
        m=0;
        System.out.println("INSERTION SORT WITH REVERSELY SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                reverseArray(temp);
                long startTime = System.currentTimeMillis();
                Sort.Insertion_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            reverselySortedMs[0][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        index = 0;
        m=0;
        System.out.println("MERGE SORT WITH REVERSELY SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                reverseArray(temp);
                long startTime = System.currentTimeMillis();
                Sort.Merge_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            reverselySortedMs[1][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }

        index = 0;
        m = 0;
        System.out.println("COUNTING SORT WITH REVERSELY SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 10; i++){
                int[] temp = sortedArrays[index];
                reverseArray(temp);
                long startTime = System.currentTimeMillis();
                Sort.Counting_Sort(temp);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 10;
            reverselySortedMs[2][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ms");
            index++;
        }
        showAndSaveChart_1("Sorting Tests on Reversely Sorted Data", sizes, reverselySortedMs);
        index = 0;
        m = 0;

        System.out.println("LINEAR SEARCH WITH RANDOM DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 1000; i++){
                int[] temp = Arrays.copyOfRange(valuesArray,0,sizes[index]);
                int keyIndex = (size / 2) + 100;
                int key = temp[keyIndex];
                long startTime = System.nanoTime();
                Search.Linear_Search(temp,key);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 1000;
            searchNs[0][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ns");
            index++;
        }
        index = 0;
        m = 0;
        System.out.println("LINEAR SEARCH WITH SORTED DATASETS");

        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 1000; i++){
                int[] temp = sortedArrays[index];
                int keyIndex = (size / 2) + 100;
                int key = temp[keyIndex];
                long startTime = System.nanoTime();
                Search.Linear_Search(temp,key);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 1000;
            searchNs[1][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ns");
            index++;
        }
        index = 0;
        m = 0;
        System.out.println("BINARY SEARCH WITH SORTED DATASETS");
        for (int size : sizes){
            long totalTime = 0;
            double averageTime = 0;
            for (int i = 0; i < 1000; i++){
                int[] temp = sortedArrays[index];
                int keyIndex = (size / 2) + 100;
                int key = temp[keyIndex];
                long startTime = System.nanoTime();
                Search.Binary_Search(temp,key);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
            }
            averageTime = (double) totalTime / 1000;
            searchNs[2][m++] = averageTime;
            System.out.println("* Average Time with " + size + " input:   " + averageTime + "ns");
            index++;
        }
        showAndSaveChart_2("Searching Tests",sizes,searchNs);
    }
    public static void showAndSaveChart_1(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Insertion Sort", doubleX, yAxis[0]);
        chart.addSeries("Merge Sort", doubleX, yAxis[1]);
        chart.addSeries("Counting Sort",doubleX,yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
    public static void showAndSaveChart_2(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Linear Search (Random Data)", doubleX, yAxis[0]);
        chart.addSeries("Linear Search (Sorted Data)",doubleX,yAxis[1]);
        chart.addSeries("Binary Search (Sorted Data)", doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}

