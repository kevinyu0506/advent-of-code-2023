package day5;

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
            String inputUrl = "https://adventofcode.com/2023/day/5/input";

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

            String testInput = "seeds: 79 14 55 13\n" + //
                    "\n" + //
                    "seed-to-soil map:\n" + //
                    "50 98 2\n" + //
                    "52 50 48\n" + //
                    "\n" + //
                    "soil-to-fertilizer map:\n" + //
                    "0 15 37\n" + //
                    "37 52 2\n" + //
                    "39 0 15\n" + //
                    "\n" + //
                    "fertilizer-to-water map:\n" + //
                    "49 53 8\n" + //
                    "0 11 42\n" + //
                    "42 0 7\n" + //
                    "57 7 4\n" + //
                    "\n" + //
                    "water-to-light map:\n" + //
                    "88 18 7\n" + //
                    "18 25 70\n" + //
                    "\n" + //
                    "light-to-temperature map:\n" + //
                    "45 77 23\n" + //
                    "81 45 19\n" + //
                    "68 64 13\n" + //
                    "\n" + //
                    "temperature-to-humidity map:\n" + //
                    "0 69 1\n" + //
                    "1 0 69\n" + //
                    "\n" + //
                    "humidity-to-location map:\n" + //
                    "60 56 37\n" + //
                    "56 93 4";

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

            // input = new ArrayList<>();

            // input.add("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
            // input.add("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
            // input.add("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1");
            // input.add("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83");
            // input.add("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36");
            // input.add("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");

            // int answer2 = solveChallenge2(input);
            // System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long solveChallenge1(List<String> input) {
        List<Long> seeds = new ArrayList<>();
        List<List<long[]>> maps = new ArrayList<>();

        int index = 0;

        while (index < input.size()) {
            String line = input.get(index);

            if (index == 0) {
                String[] values = line.split(":")[1].trim().split(" ");

                for (String val : values) {
                    seeds.add(Long.parseLong(val));
                }
                index++;
                continue;
            }

            if (line == "") {
                index++;
                continue;
            }

            if (line.indexOf("map") != -1) {
                List<long[]> map = new ArrayList<>();

                while (index + 1 < input.size() && input.get(++index).length() > 0) {
                    String[] values = input.get(index).split(" ");
                    long[] record = new long[3];
                    record[0] = Long.parseLong(values[0]);
                    record[1] = Long.parseLong(values[1]);
                    record[2] = Long.parseLong(values[2]);

                    map.add(record);
                }

                Collections.sort(map, (m, n) -> {
                    return Long.compare(m[1], n[1]);
                });

                maps.add(map);
            }

            index++;
        }

        long result = Long.MAX_VALUE;

        for (long seed : seeds) {
            // System.out.println("");

            long pos = recur(maps, 0, seed);

            result = Math.min(result, pos);
        }

        return result;
    }

    private static long recur(List<List<long[]>> maps, int index, long value) {
        // System.out.println(value + ", " + index);

        if (index == maps.size() - 1) {
            List<long[]> map = maps.get(index);

            int left = -1, right = map.size() - 1;
            
            // find largest smaller equal than
            while (left < right) {
                int mid = left + (right - left + 1) / 2;

                if (map.get(mid)[1] > value) {
                    right = mid - 1;
                } else {
                    left = mid;
                }
            }

            if (left == -1) {
                return value;
            }

            long[] prev = map.get(left);

            if (prev[1] + prev[2] - 1 >= value) {
                return prev[0] + (value - prev[1]);
            } else {
                return value;
            }
        }

        List<long[]> map = maps.get(index);

        int left = -1, right = map.size() - 1;
        
        // find largest smaller equal than
        while (left < right) {
            int mid = left + (right - left + 1) / 2;

            if (map.get(mid)[1] > value) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }

        if (left == -1) {
            return recur(maps, index + 1, value);
        }

        long[] prev = map.get(left);

        if (prev[1] + prev[2] - 1 >= value) {
            return recur(maps, index + 1, prev[0] + (value - prev[1]));
        } else {
            return recur(maps, index + 1, value);
        }
    }

    private static long solveChallenge2(List<String> input) {
        List<Long> seeds = new ArrayList<>();
        List<List<long[]>> maps = new ArrayList<>();

        int index = 0;

        while (index < input.size()) {
            String line = input.get(index);

            if (index == 0) {
                String[] values = line.split(":")[1].trim().split(" ");

                for (String val : values) {
                    seeds.add(Long.parseLong(val));
                }
                index++;
                continue;
            }

            if (line == "") {
                index++;
                continue;
            }

            if (line.indexOf("map") != -1) {
                List<long[]> map = new ArrayList<>();

                while (index + 1 < input.size() && input.get(++index).length() > 0) {
                    String[] values = input.get(index).split(" ");
                    long[] record = new long[3];
                    record[0] = Long.parseLong(values[0]);
                    record[1] = Long.parseLong(values[1]);
                    record[2] = Long.parseLong(values[2]);

                    map.add(record);
                }

                Collections.sort(map, (m, n) -> {
                    return Long.compare(m[1], n[1]);
                });

                maps.add(map);
            }

            index++;
        }

        long result = Long.MAX_VALUE;

        for (long seed : seeds) {
            // System.out.println("");

            long pos = recur(maps, 0, seed);

            result = Math.min(result, pos);
        }

        return result;
    }
}