package day6;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
            String inputUrl = "https://adventofcode.com/2023/day/6/input";

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

            String testInput = "Time:      7  15   30\nDistance:  9  40  200";

            // Get input stream from connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));

            // Read input line by line
            List<String> input = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
                System.out.println(line);
            }

            // Close resources
            reader.close();
            connection.disconnect();

            long answer1 = solveChallenge1(input);
            System.out.println("Answer: " + answer1);

            long answer2 = solveChallenge2(input);
            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long solveChallenge1(List<String> input) {
        long result = 1;

        String time = input.get(0);
        String distance = input.get(1);

        List<Integer> times = new ArrayList<>(), distances = new ArrayList<>();

        for (String t : time.split(":")[1].trim().split("\\s+")) {
            times.add(Integer.parseInt(t));
        }

        for (String d : distance.split(":")[1].trim().split("\\s+")) {
            distances.add(Integer.parseInt(d));
        }

        for (int i = 0; i < times.size(); i++) {
            int target = distances.get(i);

            int left = 0, right = times.get(i);

            while (left < right) {
                int mid = left + (right - left) / 2;

                long possible = mid * (times.get(i) - mid);

                if (possible <= target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            int from = left, to = times.get(i) - left;

            int count = to - from + 1;
            result *= count;            
        }

        return result;
    }

    private static long solveChallenge2(List<String> input) {
        String time = input.get(0);
        String distance = input.get(1);

        StringBuilder times = new StringBuilder(), distances = new StringBuilder();

        for (String t : time.split(":")[1].trim().split("\\s+")) {
            times.append(t);
        }

        for (String d : distance.split(":")[1].trim().split("\\s+")) {
            distances.append(d);
        }

        long t = Long.parseLong(times.toString()), d = Long.parseLong(distances.toString());

        System.out.println("t, d: " + t + ", " + d);

        long left = 0, right = t;

        while (left < right) {
            long mid = left + (right - left) / 2;

            long possible = mid * (t - mid);

            if (possible <= d) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        long from = left, to = t - left;

        return to - from + 1;
    }
}