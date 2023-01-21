import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class StringProblems {
    
    String removeOuterParentheses(String s) {
        String res = "", tmp = "";
        
        char[] strChars = s.toCharArray();
        ArrayList<String> sList = new ArrayList<>();

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < strChars.length; i++) {
            tmp += strChars[i];

            if (strChars[i] == '(') stack.add(strChars[i]);
            else {
                stack.pop();
                
                if (stack.isEmpty()) {
                    String newStr = tmp.substring(1, tmp.length()-1);
                    sList.add(newStr);
                    tmp = "";
                }
            }
        }

        for (String str : sList) res += str;

        return res;
    }

    String reverseWords(String s) {
        String[] words = s.split(" ");
        
        int left = 0, right = words.length-1;
        while (left < right) {
            String tmp = words[left];
            words[left] = words[right];
            words[right] = tmp;

            left++; right--;
        }

        return String.join(" ", words);
    }

    String reverseWordsUsingStack(String s) {
        Stack<String> stack = new Stack<>();

        String tmp = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') tmp += s.charAt(i);
            else {
                if (!tmp.isEmpty()) stack.push(tmp);
                tmp = "";
            }
        }
        if (!tmp.isEmpty()) stack.push(tmp);

        String res = "";
        while (!stack.isEmpty()) res += stack.pop() + " ";
        res = res.substring(0, res.length()-1);

        return res;
    }

    String largestOddNumber(String s) {
        int idx = -1;
        for (int i = s.length()-1; i >= 0; i--) {
            int v = Character.getNumericValue(s.charAt(i));
            if (v%2 != 0) {
                idx = i;
                break;
            }
        }
        
        if (idx == -1) return "";
        return s.substring(0, idx+1);
    }

    String longestCommonPrefixAlgo1(String[] strs) {
        // Idea is to do horizontal scanning
        // In each iteration we find the current longest prefix by traversing all words in each string
        // At any point prefix becomes empty, we return it
        // Time Complexity: O(S), where S is all chars of all strings

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length()-1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    String longestCommonPrefixAlgo2(String[] strs) {
        // Idea is to do vertical scanning
        // Here we compare each char of prefix for all strings staritng from first char
        // At any point, we find char which doesnt match or prefx len exceeds str len we return
        // Time Complexity: O(S)

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }

    String longestCommonPrefixAlgo3(String[] strs, int left, int right) {
        // Idea is to use divide and conquer strategy
        // We divide the arr into equal parts, find common prefix in left and right parts
        // Then combine and find common prefix among those two prefixes
        // Time Complexity: O(S)
        
        int mid = (left+right)/2;

        String leftPrefix = longestCommonPrefixAlgo3(strs, left, mid);
        String rightPrefix = longestCommonPrefixAlgo3(strs, mid+1, right);

        return commonPrefix(leftPrefix, rightPrefix);
    }

    String commonPrefix(String leftPrefix, String rightPrefix) {
        int minLen = Math.min(leftPrefix.length(), rightPrefix.length());

        for (int i = 0; i < minLen; i++) {
            if (leftPrefix.charAt(i) != rightPrefix.charAt(i))
                return leftPrefix.substring(0, i);
        }
        return leftPrefix.substring(0, minLen);
    }

    String longestCommonPrefixAlgo4(String[] strs) {
        // Idea is to apply binary search technique
        // We search in space(0, minLen) where minLen is min string len and longest possible prefix
        // Each time we divide the search space into two halves and discard one which does not have soln
        
        int minLen = Integer.MAX_VALUE;
        for (String s : strs) minLen = Math.min(minLen, s.length());

        int low = 1, high = minLen;
        while (low <= high) {
            int mid = (low+high)/2;
            if (isCommonPrefix(strs, mid)) low = mid+1;
            else high = mid-1;
        }

        return strs[0].substring(0, (low+high)/2);
    }

    boolean isCommonPrefix(String[] strs, int mid) {
        String prefix = strs[0].substring(0, mid);
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].indexOf(prefix) != 0) return false;
        }
        return true;
    }

    boolean isIsomorphic(String s, String t) {
        // Idea is to check if we can map each unique char in s to each unique char in t

        HashMap<Character, Character> sTot = new HashMap<>();
        HashMap<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i), c2 = t.charAt(i);
            
            if (!sTot.containsKey(c1) && !tToS.containsKey(c2)) {
                sTot.put(c1, c2);
                tToS.put(c2, c1);
            } else {
                if (sTot.get(c1) == null || tToS.get(c2) == null || sTot.get(c1) != c2 || tToS.get(c2) != c1)
                    return false;
            }
        }

        return true;
    }

    boolean rotateString(String s, String goal) {
        // Idea is to use technique of rolling hash
        // hash(S) = (S[0] * P**0 + S[1] * P**1 + S[2] * P**2 + ...) % MOD
        // Hash value is uniformly distributed between (0,...,mod-1), therefore if hash of two string are equal, it is very likely string are equal
        // So if we have hash(A), to calculate hash (A[1],A[2],...,A[0])
        // we subtract A[0] from hash, divide by P and add A[0] * P**(N-1)
        // Also division is done under the finite field of mod

        if (s.equals(goal)) return true;

        // define required cosntants
        int mod = 1_000_000_007, p = 113;
        int pInv = BigInteger.valueOf(p).modInverse(BigInteger.valueOf(mod)).intValue();

        // calculate hash of goal string
        long hash1 = 0, power = 1;
        for (char c : goal.toCharArray()) {
            hash1 = (hash1 + power*c) % mod;
            power = (power*p) % mod;
        }

        // calculate hash of given string
        long hash2 = 0; power = 1;
        char[] sChars = s.toCharArray();
        for (char c : sChars) {
            hash2 = (hash2 + power*c) % mod;
            power = (power*p) % mod;
        }

        // we shift each char of s to left, and calculate the new hash
        // then compare this new hash val with hash of goal string
        for (int i = 0; i < sChars.length; i++) {
            hash2 = hash2-sChars[i];
            hash2 = (hash2 + power*sChars[i]) % mod;
            hash2 = (hash2 * pInv) % mod;

            if (hash1 == hash2 && (s.substring(i+1) + s.substring(0, i+1)).equals(goal))
                return true;
        }

        return false;
    }

    boolean isAnagram(String s, String t) {
        // using maps
        if (s.length() != t.length()) return false;

        HashMap<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        for (char c : t.toCharArray()) {
            if (map.containsKey(c)) map.put(c, map.get(c)-1);
            if (map.containsKey(c) && map.get(c) == 0) map.remove(c);
        }

        return map.isEmpty();
    }

    String frequencySort(String s) {
        // Idea is store freq of chars and sort it before creating final string
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0)+1);

        // priority queue sorts chars by freq in descending order
        PriorityQueue<Pair> pQueue = new PriorityQueue<Pair>((a, b) -> b.cnt - a.cnt);
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            pQueue.add(new Pair(entry.getKey(), entry.getValue()));

        String res = "";
        while (!pQueue.isEmpty()) {
            Pair pair = pQueue.remove();
            int cnt = pair.cnt;
            while (cnt != 0) {
                res += pair.c;
                cnt--;
            }
        }

        return res;
    }

    class Pair {
        char c;
        int cnt;
        Pair(char c, int cnt) {
            this.c = c;
            this.cnt = cnt;
        }
    }

    int maxDepth(String s) {
        // Idea is to use stack base technique to get max depth
        int res = 0, cnt = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') cnt++;
            else if (c == ')') cnt--;

            res = Math.max(res, cnt);
        }

        return res;
    }

    int romanToInteger(String s) {
        // Idea is to apply roman to int rules properly

        int res = 0;
        char prev = '#';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            switch(c) {
                case 'I':
                    res += 1;
                    break;
                case 'V':
                    if (i!=0 && prev=='I') res = (res + 4)-1;
                    else res += 5;
                    break;
                case 'X':
                    if (i!=0 && prev=='I') res = (res + 9)-1;
                    else res += 10;
                    break;
                case 'L':
                    if (i!=0 && prev=='X') res = (res + 40)-10;
                    else res += 50;
                    break;
                case 'C':
                    if (i!=0 && prev=='X') res = (res + 90)-10;
                    else res += 100;
                    break;
                case 'D':
                    if (i!=0 && prev=='C') res = (res + 400)-100;
                    else res += 500;
                    break;
                case 'M':
                    if (i!=0 && prev=='C') res = (res + 900)-100;
                    else res += 1000;
                    break;
            }

            prev = c;
        }

        return res;
    }

    int atoi(String s) {
        boolean neg = false;
        long val = 0;
        char sign = '#';
        boolean nondigit = false;

        for (char c : s.toCharArray()) {
            if (c == ' ') continue;
            else if (c == '+' || c == '-') {
                if (sign != '#') return 0;
                else sign = c;
                neg = c == '-';
            } else if (c >= '0' && c <= '9') {
                if (nondigit) return 0;
                val = val*10 + Character.getNumericValue(c);
            } else if (c == '.') break; 
            else {
                nondigit = true;
            };
        }

        if (neg) val = 0 - val;

        if (val > Integer.MAX_VALUE || val < Integer.MIN_VALUE) {
            if (neg) return Integer.MIN_VALUE;
            return Integer.MAX_VALUE;
        } else return (int) val;
    }

    String longestPalindromicSubString(String s) {
        // Idea to use the fact that every palindrome has a center
        // Therefore we iterate through all 2n-1 center (n letters + n-1 space between each 2 letters)
        // We expand around the center and find len of each palidrome formed
        // Thus we get the max palindrome

        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i+1);

            int mxLen = Math.max(len1, len2);
            if (mxLen > end - start + 1) {
                start = i - (mxLen-1)/2;
                end = i + mxLen/2;
            }
        }
        
        return s.substring(start, end+1);
    }

    int expandAroundCenter(String s, int left, int right) {
        int l = left, r = right;
        while (l >= 0 && r < s.length() && (s.charAt(l) == s.charAt(r))) {
            l--;
            r++;
        }
        return r-l-1;
    }

    public static void main(String[] args) {
        StringProblems sProblems = new StringProblems();

        String ans = sProblems.longestPalindromicSubString("babad");
        System.out.println(ans);
    }
}
