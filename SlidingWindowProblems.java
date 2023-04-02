import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

public class SlidingWindowProblems {

    int maxSumSubarraySizeK(int[] arr, int K) {
        // this is a fixed size window problem

        int start = 0, end = 0; // pointer for window
        int currSum = 0, res = 0;

        while (end < arr.length) { // our window goes till end of array
            currSum += arr[end]; // add last win elem

            int win = end - start + 1;
            if (win == K) { // window size is K
                res = Math.max(res, currSum); 
                currSum -= arr[start++]; // remove first win elem
            }

            end++;
        }

        return res;
    }

    // Question
    // Given an array of size N and a positive integer K, find the first negative integer for each and every window(contiguous subarray) of size K.
    int[] firstNegInteger(int[] arr, int n, int k) {
        // fixed size window problem

        int start = 0, end = 0; // window pointers

        int[] res = new int[n-k+1];
        Queue<Integer> queue = new LinkedList<Integer>();
        int idx = 0;

        while (end < n) {
            if (arr[end] < 0) queue.add(arr[end]); // add neg value to queue

            int win = end - start + 1;
            if (win == k) {
                if (!queue.isEmpty()) {
                    int val = queue.peek();
                    res[idx++] = val; // for this window, top queue elem is the first neg elem

                    if (arr[start] == val) queue.remove(); // remove queue elem if win moves right
                    start++;
                } else res[idx++] = 0;
            }

            end++;
        }

        return res;
    }

    int countAnagrams(String pattern, String text) {
        // fixed window problem
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : pattern.toCharArray()) map.put(c, map.getOrDefault(c, 0)+1);

        char[] txtArr = text.toCharArray();

        int start = 0, end = 0; // pointers for sliding window
        int cnt = map.size(), res = 0; // cnt checks whether all map elements have been covered in window

        while (end < text.length()) {
            // if char present in map, decrease its charCnt
            // if charCnt becomes 0, reduce cnt which denotes one char of pattern has been covered
            if (map.containsKey(txtArr[end])) {
                map.put(txtArr[end], map.get(txtArr[end])-1);
                if (map.get(txtArr[end]) == 0) cnt--;
            }

            int win = end - start + 1;
            if (win == pattern.length()) {
                if (cnt == 0) res++; // if all pattern chars covered, increment the res

                // slide the window start and update map and cnt as required
                if (map.containsKey(txtArr[start])) {
                    map.put(txtArr[start], map.get(txtArr[start])+1);
                    if (map.get(txtArr[start]) == 1) cnt++;
                }

                start++;
            }

            end++;
        }

        return res;
    }
}