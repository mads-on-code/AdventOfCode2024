package Day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day25 {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("Day25\\input25.txt");
        Scanner sc = new Scanner(input);

        Map <Integer, String> table = new HashMap<>(); 
        
        int lineNumber = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                table.put(lineNumber, line);
                lineNumber++; 
            }
        }
        sc.close();

        Map <Integer, List<String>> keys = new HashMap<>();
        Map <Integer, List<String>> locks = new HashMap<>(); 

        int i = 1;
        while (i <= table.size()) {
            String firstLine = table.get(i); 
            if (firstLine == null || firstLine.isEmpty()) { // Ignorar linhas vazias
                i++;
                continue;
            }

            // Check for lock patterns
            if (firstLine.equals("#####")) {
                List<String> lockBlock = new ArrayList<>();
                for (int j = 0; j < 6 && i + j <= table.size(); j++) {
                    lockBlock.add(table.get(i + j));
                }
                locks.put(i, lockBlock);
                i += 6; 
            }
            // Check for key patterns
            else if (firstLine.equals(".....")) {
                List<String> keyBlock = new ArrayList<>();
                for (int j = 0; j < 6 && i + j <= table.size(); j++) {
                    keyBlock.add(table.get(i + j));
                }
                keys.put(i, keyBlock); 
                i += 6; 
            } else {
                i++; 
            }
        }
        // Evaluate pin heights
        Map<Integer, List<Integer>> keyPinHeights = evaluatePinHeights(keys);
        Map<Integer, List<Integer>> lockPinHeights = evaluatePinHeights(locks);
        // Output results
        //System.out.println("Key Pin Heights: " + keyPinHeights);
        //System.out.println("Lock Pin Heights: " + lockPinHeights);

        // Answer
        int answer1 = findMatches(keyPinHeights, lockPinHeights);
        System.out.println("How many unique lock/key pairs fit together without overlapping in any column?" + answer1);
    }

    public static Map<Integer, List<Integer>> evaluatePinHeights(Map<Integer, List<String>> blocks) {
        Map<Integer, List<Integer>> pinHeights = new HashMap<>();

        for (Map.Entry<Integer, List<String>> entry : blocks.entrySet()) {
            int blockStartIndex = entry.getKey();
            List<String> blockLines = entry.getValue();
            List<Integer> heights = new ArrayList<>();

            for (int col = 0; col < blockLines.get(0).length(); col++) {
                int height = 0;
                for (String line : blockLines) {
                    if (line.charAt(col) == '#') {
                        height++;
                    }
                }
                heights.add(height);
            }

            pinHeights.put(blockStartIndex, heights);
        }
        return pinHeights;
    }
        
    public static int findMatches(Map<Integer, List<Integer>> keyPinHeights, Map<Integer, List<Integer>> lockPinHeights){
        int matchCount = 0;
        for (int i = 0; i < lockPinHeights.size(); i++) {
            for (int j = 0; j < keyPinHeights.size(); j++) {
                if (ehMatch(lockPinHeights.get(i), keyPinHeights.get(i))) {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }

    public static boolean ehMatch(List<Integer> keyPinHeights,List<Integer> lockPinHeights){
        if (lockPinHeights == null || keyPinHeights == null) {
            return false;
        }
        for (Integer lock : lockPinHeights) {
            for (Integer key : keyPinHeights) {
                if (lock+key>5) {
                    return false;
                }
            }
        }
        return true;
    }
}
