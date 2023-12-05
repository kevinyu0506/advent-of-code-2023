package day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        try {
            // URL for Advent of Code input
            String inputUrl = "https://adventofcode.com/2023/day/4/input";

            // Create URL object
            URL url = new URL(inputUrl);

            // Create connection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Enable cookie management
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            // Set session cookie if needed
            connection.setRequestProperty("Cookie", "session=53616c7465645f5f5f57093c70ffc4238177fd52265306fd32454f1d242f6619b1db9a648f2147e97aebda592245235ee7d6ceb0ce4e4c5431ea6991067e437c");

            // Get input stream from connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read input line by line
            List<String> input = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
                // System.out.println(line);
            }

            // Close resources
            reader.close();
            connection.disconnect();

            // input = new ArrayList<>();

            // input.add("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
            // input.add("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
            // input.add("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1");
            // input.add("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83");
            // input.add("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36");
            // input.add("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");

            int answer1 = solveChallenge1(input);
            System.out.println("Answer: " + answer1);

            // input = new ArrayList<>();

            // input.add("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
            // input.add("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
            // input.add("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1");
            // input.add("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83");
            // input.add("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36");
            // input.add("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");

            int answer2 = solveChallenge2(input);
            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int solveChallenge1(List<String> input) {
        int result = 0;

        for (String line : input) {
            String value = line.substring(line.indexOf(":") + 1, line.length());
            String[] winningNumbers = value.substring(0, value.indexOf("|")).trim().split(" ");
            String[] numbers = value.substring(value.indexOf("|") + 1, value.length()).trim().split(" ");

            int count = 0;

            Set<String> winningNumberSet = new HashSet<>();

            for (String wn : winningNumbers) {
                if (wn.equals("")) {
                    continue;
                }
                winningNumberSet.add(wn);
            }

            for (String n : numbers) {
                if (winningNumberSet.contains(n)) {
                    count++;
                }
            }

            if (count > 0) {
                result += Math.pow(2, count - 1);
            }
        }

        return result;
    }

    private static int solveChallenge2(List<String> input) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < input.size(); i++) {
            map.put(i, 1);
        }

        for (int i = 0; i < input.size(); i++) {
            int copies = map.get(i);

            String line = input.get(i);

            String value = line.substring(line.indexOf(":") + 1, line.length());
            String[] winningNumbers = value.substring(0, value.indexOf("|")).trim().split(" ");
            String[] numbers = value.substring(value.indexOf("|") + 1, value.length()).trim().split(" ");

            int count = 0;

            Set<String> winningNumberSet = new HashSet<>();

            for (String wn : winningNumbers) {
                if (wn.equals("")) {
                    continue;
                }
                winningNumberSet.add(wn);
            }

            for (String n : numbers) {
                if (winningNumberSet.contains(n)) {
                    count++;
                }
            }

            for (int j = i+1; j <= i + count && j < input.size(); j++) {
                map.put(j, map.get(j) + copies);
            }
        }

        int result = 0;

        for (int key : map.keySet()) {
            // System.out.println("Key: " + key + ", Count: " + map.get(key));

            result += map.get(key);
        }

        return result;
    }
}