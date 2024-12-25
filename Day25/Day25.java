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

        Map<Integer, String> table = new HashMap<>();
        int lineNumber = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                table.put(lineNumber, line);
                lineNumber++;
            }
        }
        sc.close();

        Map<Integer, List<String>> keys = new HashMap<>();
        Map<Integer, List<String>> locks = new HashMap<>();

        int i = 1;
        while (i <= table.size()) {
            String firstLine = table.get(i);
            if (firstLine == null || firstLine.isEmpty()) {
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

        int uniquePairs = countValidPairs(keys, locks);
        System.out.println("How many unique lock/key pairs fit together without overlapping in any column? Answer: " + uniquePairs);
    }

    public static int countValidPairs(Map<Integer, List<String>> keys, Map<Integer, List<String>> locks) {
        int count = 0;

        for (Map.Entry<Integer, List<String>> keyEntry : keys.entrySet()) {
            List<String> keyBlock = keyEntry.getValue();

            for (Map.Entry<Integer, List<String>> lockEntry : locks.entrySet()) {
                List<String> lockBlock = lockEntry.getValue();

                if (fitsTogether(keyBlock, lockBlock)) {
                    count++;
                }
            }
        }

        return count;
    }

    public static boolean fitsTogether(List<String> keyBlock, List<String> lockBlock) {
        for (int col = 0; col < keyBlock.get(0).length(); col++) {
            for (int row = 0; row < keyBlock.size(); row++) {
                char keyChar = keyBlock.get(row).charAt(col);
                char lockChar = lockBlock.get(row).charAt(col);

                if (keyChar == '#' && lockChar == '#') {
                    return false; // Overlap found
                }
            }
        }
        return true; // No overlaps in any column
    }
}
