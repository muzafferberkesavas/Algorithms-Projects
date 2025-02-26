import java.util.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        String[] lines = readFile(path,true,true);
        assert lines != null;
        int capacity = Integer.parseInt(lines[0].split("\\s+")[0]);
        int resourceNumber = Integer.parseInt(lines[0].split("\\s+")[1]);
        int[] resources = new int[resourceNumber];
        int index = 0;
        for(String element : lines[1].split("\\s+")){
            resources[index++] = Integer.parseInt(element);
        }
        boolean[][] L = new boolean[capacity+1][resourceNumber+1];
        int maxMass = 0;
        for (int i = 0; i <= capacity; i++){
            for (int j = 0; j <= resourceNumber; j++){
                L[i][j] = L(i,j,resources,L);
                if (L[i][j] && i > maxMass){
                    maxMass = i;
                }
            }
        }
        System.out.println(maxMass);
        for (boolean[] arr : L){
            for (boolean element : arr){
                if (element){
                    System.out.print("1");
                }else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }

    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { //Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { //Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { //Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }

    public static boolean L(int mass, int index, int[] resources, boolean[][]L){
        if (index == 0){
            return (mass == 0);
        }
        if (resources[index - 1] > mass){
            return L[mass][index - 1];
        }else {
            return (L[mass][index - 1] || L[mass - resources[index-1]][index - 1]);
        }
    }
}
