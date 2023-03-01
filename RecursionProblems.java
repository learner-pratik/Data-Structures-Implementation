import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public class RecursionProblems {

    // ------------ Helper functions -----------------
    void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return;
    }
    
    // ------------- Recursion -------------------
    void printNumbersAscending(int n) {
        if (n == 0) return;
        printNumbersAscending(n-1);
        System.out.print(n+", ");
    }

    void printNumbersDescending(int n) {
        if (n == 0) return;
        System.out.print(n+", ");
        printNumbersDescending(n-1);
    }

    int sumOfNumbers(int n, int res) {
        if (n == 0) return res;
        return sumOfNumbers(n-1, res+n);
    }

    int sumOfNumbers2(int n) {
        // functional way
        if (n == 1) return n;
        return n + sumOfNumbers2(n-1);
    }

    int factorial(int n) {
        if (n == 1) return n;
        return n*factorial(n-1);
    }

    void reverseArray(int left, int right, int[] arr) {
        if (left >= right) return;
        swap(left, right, arr);
        reverseArray(left+1, right-1, arr);
    }

    boolean palindromeString(String s, int index) {
        if (index >= s.length()/2) return true;
        int n = s.length() - index - 1;
        if (s.charAt(index) != s.charAt(n)) return false;
        return palindromeString(s, index+1);
    }

    int fibonacciNumber(int n) {
        if (n <= 1) return n;
        return fibonacciNumber(n-1) + fibonacciNumber(n-2);
    }

    double power(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) return (1/x)*power(1/x, -(n+1));
        return (n%2 == 0) ? power(x*x, n/2) : x*power(x*x, n/2);
    }

    int countGoodNumbers(long n) {
        long mod = 1_000_000_007;
        long pow1 = power(4, n/2, mod) % mod;
        long pow2 = power(5, n/2 + n%2, mod) % mod;
        return (int) (pow1 * pow2 % mod);
    }

    long power(long x, long n, long mod) {
        if (n == 0) return 1;
        
        long tmp = x*x % mod;
        long res = 0;
        
        if (n%2 == 0) res = power(tmp, n/2, mod);
        else res = x*power(tmp, n/2, mod) % mod;
        
        return res;
    }

    Stack<Integer> sort(Stack<Integer> s) {
		//add code here.
		if (s.size() == 1) return s;
		
        int top = s.pop();
		Stack<Integer> s1 = sort(s);
		insert(s1, top);

		return s1;
	}

    void insert(Stack<Integer> s, int val) {
	    if (s.empty() || val >= s.peek()) s.push(val);
	    else {
	        int top = s.pop();
	        insert(s, val);
	        s.push(top);
	    }
	    return;
	}
    
    void reverseStack(Stack<Integer> s, Queue<Integer> q) {
        if (s.empty()) return;
        q.add(s.pop());
        reverseStack(s, q);
        s.push(q.poll());
    }

    ArrayList<String> generateBinarySubstrings(int n) {
        ArrayList<String> res = new ArrayList<>();
        binarySubstrings(n, res, "");
        
        return res;
    }

    void binarySubstrings(int n, ArrayList<String> resList, String s) {
        if (n == 0) {
            resList.add(new String(s));
            return;
        }
        
        binarySubstrings(n-1, resList, s+'0');
        if (s.isEmpty() || s.charAt(s.length()-1) != '1')
            binarySubstrings(n-1, resList, s+'1');
    }

    ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> res = new ArrayList<>();
        paranthesisStrings(n, res, "", 0, 0);

        return res;
    }

    void paranthesisStrings(int n, ArrayList<String> resList, String s, int o, int c) {
        if (s.length() == n*2) {
            resList.add(new String(s));
            return;
        }

        if (o < n) paranthesisStrings(n, resList, s+'(', o+1, c);
        if (c < o) paranthesisStrings(n, resList, s+')', o, c+1);
    }

    ArrayList<String> powerSet(String s) {
        ArrayList<String> res = new ArrayList<>();
        generateSubsequences(s, res, "", 0);

        return res;
    }

    void generateSubsequences(String s, ArrayList<String> resList, String tmp, int idx) {
        if (idx == s.length()) {
            if (!tmp.isEmpty()) resList.add(new String(tmp));
            return;
        }

        generateSubsequences(s, resList, tmp+s.charAt(idx), idx+1);
        generateSubsequences(s, resList, tmp, idx+1);
    }

    int mod = (int) (1e9 + 7);
    int perfectSum(int arr[],int n, int sum) { 
	    int res = countSubsets(arr, n, sum, 0, 0);
	    
	    return res % mod;
	}

    int countSubsets(int[] arr, int n, int sum, int tmp, int idx) {
        if (idx == n) {
	        if (tmp == sum) return 1;
	        else return 0;
	    }
	    
	    int cnt = 0;
	    
	    cnt += countSubsets(arr, n, sum, tmp + arr[idx], idx+1);
	    cnt += countSubsets(arr, n, sum, tmp, idx+1);
	    
	    return cnt % mod;
	}

    ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        generateCombinations(candidates, target, res, new ArrayList<Integer>(), 0);

        return res;
    }

    void generateCombinations(int[] arr, int trgt, ArrayList<ArrayList<Integer>> resList, ArrayList<Integer> temp, int idx) {
        if (trgt == 0) {
            resList.add(new ArrayList<>(temp));
            return;
        }
        if (idx == arr.length) return;

        if (arr[idx] < trgt) {
            temp.add(arr[idx]);
            generateCombinations(arr, trgt - arr[idx], resList, temp, idx);
            temp.remove(temp.size()-1);
        }
        generateCombinations(arr, trgt, resList, temp, idx+1);
    }

    ArrayList<ArrayList<Integer>> combinationSum2(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        Arrays.sort(candidates);
        generateCombinations2(candidates, target, res, new ArrayList<>(), 0);

        return res;
    }

    void generateCombinations2(int[] arr, int trgt, ArrayList<ArrayList<Integer>> resList, ArrayList<Integer> temp, int idx) {
        if (trgt == 0) {
            resList.add(new ArrayList<>(temp));
            return;
        }

        for (int i = idx; i < arr.length; i++) {
            if (arr[i] > trgt) break;
            
            if (i > idx && arr[i] == arr[i-1]) continue;

            temp.add(arr[i]);
            generateCombinations2(arr, trgt - arr[i], resList, temp, idx+1);
            temp.remove(temp.size()-1);
        }
    }

    ArrayList<Integer> subsetSum(int[] nums) {
        ArrayList<Integer> res = new ArrayList<>();
        generateSums(nums, res, 0, 0);

        return res;
    }

    void generateSums(int[] arr, ArrayList<Integer> resList, int ans, int idx) {
        if (idx == arr.length) {
            resList.add(ans);
            return;
        }

        generateSums(arr, resList, ans + arr[idx], idx+1);
        generateSums(arr, resList, ans, idx+1);
    }

    ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);
        generateSubsets(nums, res, new ArrayList<>(), 0);

        return res;
    }

    void generateSubsets(int[] arr, ArrayList<ArrayList<Integer>> resList, ArrayList<Integer> temp, int idx) {
        resList.add(new ArrayList<>(temp));

        for (int i = idx; i < arr.length; i++) {
            if (i > idx && arr[i] == arr[i-1]) continue;
            
            temp.add(arr[i]);
            generateSubsets(arr, resList, temp, i+1);
            temp.remove(temp.size()-1);
        }
    }

    ArrayList<ArrayList<Integer>> combinationSum3(int k, int n) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        generateCombinations3(k, n, res, new ArrayList<>(), 1);

        return res;
    }

    void generateCombinations3(int cnt, int trgt, ArrayList<ArrayList<Integer>> resList, ArrayList<Integer> temp, int num) {
        if (cnt == 0) {
            if (trgt == 0) resList.add(new ArrayList<>(temp));
            return;
        }

        for (int i = num; i <= 9; i++) {
            if (i <= trgt) {
                temp.add(i);
                generateCombinations3(cnt-1, trgt-i, resList, temp, i+1);
                temp.remove(temp.size()-1);
            }
        }
    }

    ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> res = new ArrayList<>();
        HashMap<Character, String> map = generateDigitsMap();

        generateLetterCombinations(digits, map, res, "", 0);

        return res;
    }

    void generateLetterCombinations(String digits, HashMap<Character, String> digitMap, ArrayList<String> resList, String tmp, int idx) {
        if (idx == digits.length()) {
            resList.add(new String(tmp));
            return;
        }

        String s = digitMap.get(digits.charAt(idx));
        for (char c : s.toCharArray()) {
            tmp += c;
            generateLetterCombinations(digits, digitMap, resList, tmp, idx+1);
            tmp = tmp.substring(0, tmp.length()-1);
        }
    }

    HashMap<Character, String> generateDigitsMap() {
        HashMap<Character, String> map = new HashMap<>();

        map.put('1', "");
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        return  map;
    }

    public static void main(String[] args) {
        RecursionProblems rProblems = new RecursionProblems();
        
        ArrayList<String> ans = rProblems.letterCombinations("23");
        System.out.println(ans.toString());
    }
}
