package day7;

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
            String inputUrl = "https://adventofcode.com/2023/day/7/input";

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

            // String testInput = "Time:      7  15   30\nDistance:  9  40  200";
            String testInput = "32T3K 765\nT55J5 684\nKK677 28\nKTJJT 220\nQQQJA 483";

            // Get input stream from connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));

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

            long answer1 = solveChallenge1(input);
            System.out.println("Answer: " + answer1);

            long answer2 = solveChallenge2(input);
            System.out.println("Answer: " + answer2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long solveChallenge1(List<String> input) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('2', 0);
        map.put('3', 1);
        map.put('4',2);
        map.put('5', 3);
        map.put('6', 4);
        map.put('7', 5);
        map.put('8', 6);
        map.put('9', 7);
        map.put('T', 8);
        map.put('J', 9);
        map.put('Q', 10);
        map.put('K', 11);
        map.put('A', 12);

        class Record {
            String card;
            int bid;
            int[] bucket;
            int score = 0;
            int pairs = 0;

            Record(String card, int bid) {
                this.card = card;
                this.bid = bid;
                this.bucket = new int[13];

                for (char c : card.toCharArray()) {
                    this.score = Math.max(this.score, ++this.bucket[map.get(c)]);
                    
                    if (this.bucket[map.get(c)] == 2) {
                        pairs++;
                    }
                }
            }
        }

        List<Record> records = new ArrayList<>();

        for (String hand : input) {
            String[] s = hand.split(" ");
            String card = s[0];
            int bid = Integer.parseInt(s[1]);

            Record record = new Record(card, bid);
            records.add(record);
        }

        Collections.sort(records, (m, n) -> {
            if (m.score != n.score) {
                return Integer.compare(n.score, m.score);
            } else {
                if (m.pairs != n.pairs) {
                    return Integer.compare(n.pairs, m.pairs);
                }

                for (int i = 0; i < m.card.length(); i++) {
                    char mChar = m.card.charAt(i), nChar = n.card.charAt(i);
                    int mVal = map.get(mChar), nVal = map.get(nChar);

                    if (mVal != nVal) {
                        return Integer.compare(nVal, mVal);
                    }
                }

                return 0;
            }
        });

        long result = 0;
        int rank = 1;

        for (int i = records.size() - 1; i >= 0; i--) {
            // System.out.println("card, rank: " + records.get(i).card + ", " + rank);
            result += rank * records.get(i).bid;

            rank++;
        }

        return result;
    }

    private static long solveChallenge2(List<String> input) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('J', 0);
        map.put('2', 1);
        map.put('3', 2);
        map.put('4',3);
        map.put('5', 4);
        map.put('6', 5);
        map.put('7', 6);
        map.put('8', 7);
        map.put('9', 8);
        map.put('T', 9);
        map.put('Q', 10);
        map.put('K', 11);
        map.put('A', 12);

        class Record {
            String card;
            int bid;
            int[] bucket;
            int largest;
            int second;

            Record(String card, int bid) {
                this.card = card;
                this.bid = bid;
                this.bucket = new int[13];

                for (char c : card.toCharArray()) {
                    this.bucket[map.get(c)]++;
                }

                int joker = bucket[0];

                for (int i = 1; i <= 12; i++) {
                    int count = bucket[i];

                    if (count > largest) {
                        second = largest;
                        largest = count;
                    } else if (count > second) {
                        second = count;
                    }
                }

                largest += joker;
            }
        }

        List<Record> records = new ArrayList<>();

        for (String hand : input) {
            String[] s = hand.split(" ");
            String card = s[0];
            int bid = Integer.parseInt(s[1]);

            Record record = new Record(card, bid);
            records.add(record);
        }

        Collections.sort(records, (m, n) -> {
            if (m.largest != n.largest) {
                return Integer.compare(n.largest, m.largest);
            } else {
                if (m.second != n.second) {
                    return Integer.compare(n.second, m.second);
                }

                for (int i = 0; i < m.card.length(); i++) {
                    char mChar = m.card.charAt(i), nChar = n.card.charAt(i);
                    int mVal = map.get(mChar), nVal = map.get(nChar);

                    if (mVal != nVal) {
                        return Integer.compare(nVal, mVal);
                    }
                }

                return 0;
            }
        });

        long result = 0;
        int rank = 1;

        for (int i = records.size() - 1; i >= 0; i--) {
            // System.out.println("card, rank: " + records.get(i).card + ", " + rank);
            result += rank * records.get(i).bid;

            rank++;
        }

        return result;
    }
}