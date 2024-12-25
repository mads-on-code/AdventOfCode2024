import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("Day2\\input2.txt");
        Scanner sc = new Scanner(input);
        Map <Integer, List<Integer>> table = new HashMap<>(); 
        
        int lineNumber = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lines = line.split("\\s+"); // Split by whitespace
            List<Integer> values = new ArrayList<>();
            for (String value : lines) {
                values.add(Integer.parseInt(value));
            }
            table.put(lineNumber, values);
            lineNumber++;
        }
        sc.close();

        int safeScore = countSafe(table);
        int updatedScore = countUpdatedSafe(table);
        
        System.out.println("How many reports are safe? Answer:" + safeScore); // Parte 1 do exercicio
        System.out.println("Considering the Problem Dampener, how many reports are now safe? Answer:" + updatedScore); // Parte 2 do exercicio
    }

    public static boolean isSafe(List<Integer> line) {
        if (line == null || line.size() < 2) {
            return false;
        }
        // "The levels are either all increasing or all decreasing"
        boolean isAscending = true;
        boolean isDescending = true;

        for (int i = 0; i < line.size() - 1; i++) {
            int diff = line.get(i + 1) - line.get(i);

            if (Math.abs(diff) > 3 || diff == 0) { // "Any two adjacent levels differ by at least 1 and at most 3"
                return false;
            }

            if (diff > 0) {
                isDescending = false;
            } else if (diff < 0) {
                isAscending = false;
            }
        }
        return isAscending || isDescending;
    }
    
    public static int countSafe(Map<Integer, List<Integer>> table) {
        int count = 0;
        for (Map.Entry<Integer, List<Integer>> entry : table.entrySet()) {
            if (isSafe(entry.getValue())) {
                count++;
            }
        }
        return count;
    }

    public static int countUpdatedSafe(Map<Integer, List<Integer>> table) {
        int count = 0;
        for (Map.Entry<Integer, List<Integer>> entry : table.entrySet()) {
            if (isUpdatedSafe(entry.getValue())) {
                count++;
            }
        }
        return count;
    }

    public static boolean isUpdatedSafe(List<Integer> line) {
        boolean isSafe = false;
        if (!isSafe(line)) {
            for (int i = 0; i < line.size() - 1; i++) {
                line.remove(line.get(i));
                if (isSafe(line)) {
                    isSafe = true;
                }
                
            }
        }    
        return isSafe;
    }
}
