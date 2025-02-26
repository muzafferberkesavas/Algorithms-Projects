import java.util.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz1 {
    public static void main(String[] args) throws IOException {

        // TODO: Use the first command line argument (args[0]) as the file to read the input from
        // TODO: Your code goes here
        // TODO: Print the solution to STDOUT

        Locale.setDefault(Locale.ENGLISH);

        String path = args[0];
        String[] lines = readFile(path,true,true);
        int titleIndex = 0;
        for (int i = 0; i < lines.length; i++){
            if (lines[i].equals("...")){
                titleIndex = i;
                break;
            }
        }
        int j = 0;
        String[] wordsIgnore = Arrays.copyOfRange(lines,0,titleIndex);
        String[] titles = Arrays.copyOfRange(lines,titleIndex+1,lines.length);
        String[][] array = new String[titles.length][];
        for (String title : titles){
            String[] words = title.split("\\s+");
            String[] lowerWords = new String[words.length];
            int i = 0;
            for (String word : words){
                lowerWords[i++] = word.toLowerCase();
            }
            array[j++] = lowerWords;
        }
        ArrayList<String> wordsToSort = new ArrayList<>();
        for (int k = 0; k < array.length; k++ ){

            for (int l = 0; l < array[k].length; l++){
                String element = array[k][l];
                boolean isIgnorable = false;
                for (String ignoreWord : wordsIgnore ){
                    if (ignoreWord.equals(element)){
                        isIgnorable = true;
                        break;
                    }
                }
                if (!isIgnorable){
                    wordsToSort.add(element);
                }
            }
        }
        String[] nonSortedArr = wordsToSort.toArray(new String[0]);
        sort(nonSortedArr);
        int count = 0;
        for (int index = 0; index < nonSortedArr.length; index++){
            int count_2 = 0;
            if (index != 0 && nonSortedArr[index-1].equals(nonSortedArr[index])){
                count++;
            }else {
                count = 0;
            }
            String key =  nonSortedArr[index];
            loops:
            for (String[] title : array){
                for (int i = 0; i < title.length; i++){
                    if (key.equals(title[i])){
                        if (count_2 == count){
                            for (int l = 0; l < title.length; l++){
                                if (l != i){
                                    System.out.print(title[l]+" ");
                                }else {
                                    String word = title[l].toUpperCase();
                                    System.out.print(word+" ");
                                }
                            }
                            System.out.println();
                            break loops;
                        }else {
                            count_2++;
                        }
                    }
                }
            }
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

    public static void sort(String[] array){
        int length = array.length;
        for (int i = 1; i < length; i++){
            String key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareToIgnoreCase(key) > 0){
                array[j+1] = array[j];
                j = j - 1;
            }
            array[j+1] = key;
        }
    }
    }

