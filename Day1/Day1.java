import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("input1.txt");
        Scanner sc = new Scanner(input);
        
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] columns = line.split("\\s+"); // Ignorar espacos
            if (columns.length >= 2) { 
                left.add(Integer.parseInt(columns[0]));
                right.add(Integer.parseInt(columns[1]));
            }
        }
        sc.close();

        // Ordenar por ordem ascendente as 2 listas
        Collections.sort(left);
        Collections.sort(right);

        int[] array1 = left.stream().mapToInt(Integer::intValue).toArray();
        int[] array2 = right.stream().mapToInt(Integer::intValue).toArray();
        int[] distances = calculateDistances(array1,array2);
        int total = 0;
        for (int i = 0; i < distances.length; i++) {
            total += distances[i];
        }
        System.out.println("A distancia total entre as listas eh: " + total); // Answer Parte 1

        int similarity = similarityScore(left, right);
        System.out.println("A semelhanca entre as listas eh: " + similarity); // Answer Parte 2
    }

    public static int[] calculateDistances(int[] left, int[] right){
        int[] distances = new int[left.length];
        if (left.length == right.length) {
            for (int i = 0; i < left.length; i++) {
                distances[i] = Math.abs( left[i] - right[i]);
            }
        }
        return distances;
    }

    public static int similarityScore(ArrayList<Integer> left, ArrayList<Integer> right) {
        int total = 0;
        for (int i = 0; i < left.size(); i++) {
            int number = left.get(i);
    
            int count = 0;
            for (int j = 0; j < right.size(); j++) {
                if (right.get(j).equals(number)) {
                    total+=number;
                }
            }
        }
        return total;
    }
    
}