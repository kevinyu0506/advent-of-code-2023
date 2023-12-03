package day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        try {
            // URL for Advent of Code input
            String inputUrl = "https://adventofcode.com/2023/day/2/input";

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

            // input = new ArrayList<>(); // 8
            // input.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
            // input.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
            // input.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
            // input.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
            // input.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");

            int answer1 = solveDay2Challenge1(input);
            System.out.println("Answer: " + answer1);

            // input = new ArrayList<>(); // 2286
            // input.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
            // input.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
            // input.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
            // input.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
            // input.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");

            int answer2 = solveDay2Challenge2(input);
            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int solveDay2Challenge1(List<String> input) {
        Map<String, Integer> map = new HashMap<>();
        map.put("red", 0);
        map.put("green", 1);
        map.put("blue", 2);

        int[] bag = new int[3];
        bag[0] = 12;
        bag[1] = 13;
        bag[2] = 14;

        int result = 0;

        for (String line : input) {
            String  gameIDStr = line.substring(0, line.indexOf(":"));
            int gameID = Integer.parseInt(gameIDStr.split(" ")[1]);

            String gameSubsetsStr = line.substring(line.indexOf(":") + 1, line.length());
            String[] gameSubsets = gameSubsetsStr.split(";");

            boolean isPossible = true;

            for (String gameSubset : gameSubsets) {
                String[] subset = gameSubset.split(",");
                int[] bucket = new int[3];

                for (String record : subset) {
                    String trimmed = record.trim();
                    String[] rec = trimmed.split(" ");
                    
                    int count = Integer.parseInt(rec[0]);
                    String color = rec[1];

                    bucket[map.get(color)] += count;

                    if (bucket[map.get(color)] > bag[map.get(color)]) {
                        isPossible = false;
                    }
                }
            }

            if (isPossible) {
                result += gameID;
            }
        }

        return result;
    }

    private static int solveDay2Challenge2(List<String> input) {
        Map<String, Integer> map = new HashMap<>();
        map.put("red", 0);
        map.put("green", 1);
        map.put("blue", 2);

        int result = 0;

        for (String line : input) {
            String  gameIDStr = line.substring(0, line.indexOf(":"));
            // int gameID = Integer.parseInt(gameIDStr.split(" ")[1]);

            String gameSubsetsStr = line.substring(line.indexOf(":") + 1, line.length());
            String[] gameSubsets = gameSubsetsStr.split(";");

            int[] bucket = new int[3];

            for (String gameSubset : gameSubsets) {
                String[] subset = gameSubset.split(",");

                for (String record : subset) {
                    String trimmed = record.trim();
                    String[] rec = trimmed.split(" ");
                    
                    int count = Integer.parseInt(rec[0]);
                    String color = rec[1];

                    bucket[map.get(color)] = Math.max(count, bucket[map.get(color)]);
                }
            }

            int power = 1;

            for (int num : bucket) {
                power *= num;
            }

            result += power;
        }

        return result;
    }
}
