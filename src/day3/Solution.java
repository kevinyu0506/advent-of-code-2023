package day3;

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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        try {
            // URL for Advent of Code input
            String inputUrl = "https://adventofcode.com/2023/day/3/input";

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
            
            // input.add("467..114..");
            // input.add("...*......");
            // input.add("..35..633.");
            // input.add("......#...");
            // input.add("617*......");
            // input.add(".....+.58.");
            // input.add("..592.....");
            // input.add("......755.");
            // input.add("...$.*....");
            // input.add(".664.598..");

            int answer1 = solveDay3Challenge1(input);
            System.out.println("Answer: " + answer1);

            // input = new ArrayList<>();
            
            // input.add("467..114..");
            // input.add("...*......");
            // input.add("..35..633.");
            // input.add("......#...");
            // input.add("617*......");
            // input.add(".....+.58.");
            // input.add("..592.....");
            // input.add("......755.");
            // input.add("...$.*....");
            // input.add(".664.598..");

            int answer2 = solveDay3Challenge2(input);
            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int solveDay3Challenge1(List<String> input) {
        int[][] DIRECTIONS = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        int result = 0;

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            int ptr = 0;

            while (ptr < line.length()) {
                int start = ptr;

                while (start < line.length() && !Character.isDigit(line.charAt(start))) {
                    start++;
                }

                StringBuilder number = new StringBuilder();
                boolean isValid = false;

                int end = start;

                while (end < line.length() && Character.isDigit(line.charAt(end))) {
                    // row: i, col: end

                    for (int[] dir : DIRECTIONS) {
                        int nr = i + dir[0], nc = end + dir[1];

                        if (nr < 0 || nr >= input.size() || nc < 0 || nc >= line.length()) {
                            continue;
                        }

                        if (input.get(nr).charAt(nc) != '.' && !Character.isDigit(input.get(nr).charAt(nc))) {
                            isValid = true;
                        }
                    }

                    number.append(line.charAt(end));
                    end++;
                }

                if (isValid) {
                    int value = Integer.parseInt(number.toString());
                    result += value;
                }

                ptr = end;
            }
        }

        return result;
    }

    private static int solveDay3Challenge2(List<String> input) {
        int[][] DIRECTIONS = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        Map<String, List<Integer>> gears = new HashMap<>();

        int result = 0;

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            int ptr = 0;

            while (ptr < line.length()) {
                int start = ptr;

                while (start < line.length() && !Character.isDigit(line.charAt(start))) {
                    start++;
                }

                StringBuilder number = new StringBuilder();
                boolean isValid = false;
                Set<String> gear = new HashSet<>();

                int end = start;

                while (end < line.length() && Character.isDigit(line.charAt(end))) {
                    // row: i, col: end

                    for (int[] dir : DIRECTIONS) {
                        int nr = i + dir[0], nc = end + dir[1];

                        if (nr < 0 || nr >= input.size() || nc < 0 || nc >= line.length()) {
                            continue;
                        }

                        if (input.get(nr).charAt(nc) != '.' && !Character.isDigit(input.get(nr).charAt(nc))) {
                            gear.add(nr + ", " + nc);
                            isValid = true;
                        }
                    }

                    number.append(line.charAt(end));
                    end++;
                }

                if (isValid) {
                    int value = Integer.parseInt(number.toString());

                    for (String g : gear) {
                        gears.putIfAbsent(g, new ArrayList<>());
                        gears.get(g).add(value);
                    }
                }

                ptr = end;
            }
        }

        for (String g : gears.keySet()) {
            List<Integer> values = gears.get(g);

            if (values.size() == 2) {
                result += (values.get(0) * values.get(1));
            }
        }

        return result;
    }
}
