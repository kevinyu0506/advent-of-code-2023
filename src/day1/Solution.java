package day1;

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
            String inputUrl = "https://adventofcode.com/2023/day/1/input";

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
            }

            // Close resources
            reader.close();
            connection.disconnect();

            int answer1 = solveDay1Challenge1(input);

            System.out.println("Answer: " + answer1);

            // List<String> testInput = new ArrayList<>(); // 281
            // testInput.add("two1nine");
            // testInput.add("eightwothree");
            // testInput.add("abcone2threexyz");
            // testInput.add("xtwone3four");
            // testInput.add("4nineeightseven2");
            // testInput.add("zoneight234");
            // testInput.add("7pqrstsixteen");

            long answer2 = solveDay1Challenge2(input);

            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int solveDay1Challenge1(List<String> input) {
        int result = 0;

        for (String line : input) {
            int start = 0, end = line.length() - 1;

            while (!Character.isDigit(line.charAt(start))) {
                start++;
            }

            while (!Character.isDigit(line.charAt(end))) {
                end--;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(line.charAt(start));
            sb.append(line.charAt(end));

            result += Integer.parseInt(sb.toString());
        }

        return result;
    }

    private static long solveDay1Challenge2(List<String> input) {
        long result = 0;

        Map<String, Integer> dictionary = new HashMap<>();
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        dictionary.put("3", 3);
        dictionary.put("4", 4);
        dictionary.put("5", 5);
        dictionary.put("6", 6);
        dictionary.put("7", 7);
        dictionary.put("8", 8);
        dictionary.put("9", 9);
        dictionary.put("one", 1);
        dictionary.put("two", 2);
        dictionary.put("three", 3);
        dictionary.put("four", 4);
        dictionary.put("five", 5);
        dictionary.put("six", 6);
        dictionary.put("seven", 7);
        dictionary.put("eight", 8);
        dictionary.put("nine", 9);

        Node root = new Node("", false);

        for (String w : dictionary.keySet()) {
            Node ptr = root;
            boolean isEnd = false;

            for (int i = 0; i < w.length(); i++) {
                if (!ptr.nextCharacter.containsKey(w.charAt(i))) {
                    if (i == w.length() - 1) {
                        isEnd = true;
                    }

                    Node next = (isEnd) ? new Node(w, true) : new Node("", false);
                    ptr.nextCharacter.put(w.charAt(i), next);
                }

                ptr = ptr.nextCharacter.get(w.charAt(i));
            }
        }

        for (String line : input) {
            int first = 0, last = 0, index = 0;

            while (index < line.length()) {
                String value = parseNumber(line, index, root);

                if (value == "") {
                    index++;
                    continue;
                }

                if (first == 0) {
                    first = dictionary.get(value);
                } else {
                    last = dictionary.get(value);
                }

                // index = index + value.length();
                index++; // handle line such as oneight
            }

            if (last == 0) {
                last = first;
            }

            // System.out.println("line: " + line + ", first: " + first + ", last: " + last + ". combined value: " + ((first * 10) + last));

            result += ((first * 10) + last);
        }


        return result;
    }

    private static String parseNumber(String line, int index, Node node) {
        Node ptr = node;

        while (index < line.length() && ptr.nextCharacter.containsKey(line.charAt(index))) {
            ptr = ptr.nextCharacter.get(line.charAt(index));

            if (ptr.end) {
                return ptr.value;
            }

            index++;
        }

        return "";
    }

    private static class Node {
        String value;
        boolean end;
        Map<Character, Node> nextCharacter;

        Node(String value, boolean end) {
            this.value = value;
            this.end = end;
            this.nextCharacter = new HashMap<>();
        }
    }
}
